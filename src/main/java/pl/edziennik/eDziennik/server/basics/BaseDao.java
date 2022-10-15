package pl.edziennik.eDziennik.server.basics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
@Repository
@SuppressWarnings("unchecked")
public abstract class BaseDao<E extends Serializable> implements IBaseDao<E> {

    private Class<E> clazz;

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
    public E saveOrUpdate(final E entity){
        if (em.contains(entity)){
            E savedEntity = em.merge(entity);
            return savedEntity;
        }
        em.persist(entity);
        return entity;
    }

    @Override
    public void remove(final E entity) {
        em.remove(entity);
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

}
