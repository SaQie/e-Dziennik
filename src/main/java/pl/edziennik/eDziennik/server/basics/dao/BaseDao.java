package pl.edziennik.eDziennik.server.basics.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import pl.edziennik.eDziennik.server.basics.entity.AbstractEntity;
import pl.edziennik.eDziennik.server.basics.dto.Page;
import pl.edziennik.eDziennik.server.exceptions.BusinessException;
import pl.edziennik.eDziennik.server.exceptions.EntityNotFoundException;
import pl.edziennik.eDziennik.server.utils.ResourceCreator;

import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Basics dao class
 *
 * @param <ENTITY>
 */
@Repository
@SuppressWarnings("unchecked")
@Transactional(readOnly = true)
public abstract class BaseDao<ENTITY extends AbstractEntity> implements IBaseDao<ENTITY> {

    private final Class<ENTITY> clazz;

    @Autowired
    private ResourceCreator resourceCreator;

    @PersistenceContext
    protected EntityManager em;

    public BaseDao() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        clazz = (Class) pt.getActualTypeArguments()[0];
    }


    @Override
    public Optional<ENTITY> find(final Long id) {
        return Optional.ofNullable(em.find(clazz, id));
    }

    @Override
    public List<ENTITY> findAll() {
        return em.createQuery("from " + clazz.getName()).getResultList();
    }

    @Override
    public Page<List<ENTITY>> findAll(int page, int size) {
        if (page <= 0 || size <= 0) {
            throw new BusinessException("Page or size cannot be negative or zero");
        }
        Query query = em.createQuery("select count(c.id) from " + clazz.getName() + " c");
        long itemsCount = (long) query.getSingleResult();

        BigDecimal pagesCount = new BigDecimal(itemsCount)
                .divide(new BigDecimal(size), RoundingMode.CEILING);

        Query findAllQuery = em.createQuery("from " + clazz.getName());
        findAllQuery.setMaxResults(size);
        findAllQuery.setFirstResult((page - 1) * size);

        List<ENTITY> resultList = findAllQuery.getResultList();

        Page<List<ENTITY>> pageObj = new Page<>();
        pageObj.setActualPage(page);
        pageObj.setItemsTotalCount(itemsCount);
        pageObj.setItemsOnPage(resultList.size());
        pageObj.setPagesCount(pagesCount.intValue());
        pageObj.setEntity(resultList);

        return pageObj;
    }

    @Override
    @Transactional
    public List<ENTITY> saveAll(List<ENTITY> entities) {
        List<ENTITY> savedEntities = new ArrayList<>();
        for (ENTITY entity : entities) {
            if (!entity.isNew()) {
                savedEntities.add(em.merge(entity));
            } else {
                em.persist(entity);
                savedEntities.add(entity);
            }
        }
        return savedEntities;
    }

    @Override
    @Transactional
    public ENTITY saveOrUpdate(final ENTITY entity) {
        if (!entity.isNew()) {
            return em.merge(entity);
        }
        em.persist(entity);
        return entity;
    }


    @Override
    @Transactional
    public void remove(final ENTITY entity) {
        if (entity == null) {
            throw new EntityNotFoundException(createNotFoundExceptionMessage(clazz.getSimpleName()));
        }
        em.remove(em.contains(entity) ? entity : em.merge(entity));
    }

    @Override
    public String getClazzName() {
        if (clazz != null) {
            return this.clazz.getCanonicalName();
        }
        return "";
    }


    @Override
    public <T> Optional<T> find(Class<T> clazz, Long id) {
        return Optional.ofNullable(em.find(clazz, id));
    }


    @Override
    public EntityManager getEm() {
        return em;
    }

    @Override
    @Transactional
    public void remove(Long id) {
        ENTITY entity = em.find(clazz, id);
        if (entity != null) {
            em.remove(em.contains(entity) ? entity : em.merge(entity));
        }
        throw new EntityNotFoundException(createNotFoundExceptionMessage(id, clazz.getSimpleName()));
    }


    @Override
    public boolean isExist(Long id) {
        ENTITY entity = em.find(clazz, id);
        return entity != null;
    }

    @Override
    public <T> boolean isExist(Class<T> clazz, Long id) {
        T t = em.find(clazz, id);
        return t != null;
    }

    @Override
    public ENTITY get(Long id) {
        ENTITY entity = em.find(clazz, id);
        if (entity == null) {
            throw new EntityNotFoundException(createNotFoundExceptionMessage(id, clazz.getSimpleName()));
        }
        return entity;
    }

    @Override
    public <T> T get(Class<T> clazz, Long id) {
        T t = em.find(clazz, id);
        if (t == null) {
            throw new EntityNotFoundException(createNotFoundExceptionMessage(id, clazz.getSimpleName()));
        }
        return t;
    }

    @Override
    public <T> void findWithExecute(Class<T> clazz, Long id, Consumer<T> consumer) {
        T t = em.find(clazz, id);
        if (t == null) {
            throw new EntityNotFoundException(createNotFoundExceptionMessage(id, clazz.getSimpleName()));
        }
        consumer.accept(t);
    }

    @Override
    public <T> void findWithExecute(Long id, Consumer<T> consumer) {
        ENTITY entity = em.find(clazz, id);
        if (entity == null) {
            throw new EntityNotFoundException(createNotFoundExceptionMessage(id, clazz.getSimpleName()));
        }
        consumer.accept((T) entity);
    }

    private String createNotFoundExceptionMessage(Long id, String className) {
        return resourceCreator.of("not.found.message", id, className);
    }

    private String createNotFoundExceptionMessage(String className){
        return resourceCreator.of("not.found.message.object", className);
    }

}
