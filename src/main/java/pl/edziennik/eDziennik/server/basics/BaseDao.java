package pl.edziennik.eDziennik.server.basics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import pl.edziennik.eDziennik.exceptions.EntityNotFoundException;
import pl.edziennik.eDziennik.server.utils.ResourceCreator;

import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Repository
@SuppressWarnings("unchecked")
@Transactional(readOnly = true)
public abstract class BaseDao<E extends Serializable> implements IBaseDao<E> {

    private final Class<E> clazz;

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
    public Optional<E> find(final Long id){
        return Optional.ofNullable(em.find(clazz, id));
    }

    @Override
    public List<E> findAll(){
        return em.createQuery("from " + clazz.getName()).getResultList();
    }

    @Override
    @Transactional
    public List<E> saveAll(List<E> entities) {
        List<E> savedEntities = new ArrayList<>();
        for (E entity : entities) {
            if (em.contains(entity)){
                savedEntities.add(em.merge(entity));
            }else{
                em.persist(entity);
                savedEntities.add(entity);
            }
        }
        return savedEntities;
    }

    @Override
    @Transactional
    public E saveOrUpdate(final E entity){
        if (em.contains(entity)){
            return em.merge(entity);
        }
        em.persist(entity);
        return entity;
    }

    @Override
    @Transactional
    public void remove(final E entity) {
        em.remove(em.contains(entity) ? entity : em.merge(entity));
    }

    @Override
    public String getClazzName() {
        if (clazz != null){
            return this.clazz.getCanonicalName();
        }
        return "";
    }

    @Override
    public <T> Optional<T> find(Class<T> clazz, Long id) {
        return Optional.ofNullable(em.find(clazz,id));
    }

    @Override
    public EntityManager getEm(){
        return em;
    }

    @Override
    @Transactional
    public void remove(Long id) {
        E e = em.find(clazz, id);
        if (e != null) {
            em.remove(e);
        }
    }

    @Override
    public boolean isExist(Long id) {
        E e = em.find(clazz, id);
        return e != null;
    }

    @Override
    public <T> boolean isExist(Class<T> clazz, Long id) {
        T t = em.find(clazz, id);
        return t != null;
    }

    @Override
    public E get(Long id) {
        E e = em.find(clazz, id);
        if (e == null){
            throw new EntityNotFoundException(createNotFoundExceptionMessage(id));
        }
        return e;
    }

    @Override
    public <T> T get(Class<T> clazz, Long id) {
        T t = em.find(clazz, id);
        if (t == null){
            throw new EntityNotFoundException(createNotFoundExceptionMessage(id));
        }
        return t;
    }

    @Override
    public <T> void findWithExecute(Class<T> clazz, Long id, Consumer<T> consumer) {
        T t = em.find(clazz, id);
        if (t == null){
            throw new EntityNotFoundException(createNotFoundExceptionMessage(id));
        }
        consumer.accept(t);
    }

    @Override
    public <T> void findWithExecute(Long id, Consumer<T> consumer) {
        E e = em.find(clazz, id);
        if (e == null){
            throw new EntityNotFoundException(createNotFoundExceptionMessage(id));
        }
        consumer.accept((T) e);
    }

        private String createNotFoundExceptionMessage(Long id){
            return resourceCreator.of("not.found.message", id);
        }

}
