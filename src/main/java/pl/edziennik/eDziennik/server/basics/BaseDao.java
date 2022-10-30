package pl.edziennik.eDziennik.server.basics;

import liquibase.pro.packaged.e;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Repository
@SuppressWarnings("unchecked")
@Transactional
public abstract class BaseDao<E extends Serializable> implements IBaseDao<E> {

    private final Class<E> clazz;

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
    public E saveOrUpdate(final E entity){
        if (em.contains(entity)){
            return em.merge(entity);
        }
        em.persist(entity);
        return entity;
    }

    @Override
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
    public <T> T get(Class<T> clazz, Long id) {
        return em.find(clazz,id);
    }

    @Override
    public EntityManager getEm(){
        return em;
    }

    @Override
    public void remove(Long id) {
        E e = em.find(clazz, id);
        if (e != null) {
            em.remove(e);
        }
    }

    @Override
    public boolean exist(Long id) {
        E e = em.find(clazz, id);
        return e != null;
    }

    @Override
    public <T> boolean exist(Class<T> clazz, Long id) {
        T t = em.find(clazz, id);
        return t != null;
    }

    @Override
    public E findWithExistCheck(Long id) {
        E e = em.find(clazz, id);
        if (e == null){
            throw new EntityNotFoundException(clazz.getSimpleName() + " with id " + id + " not found");
        }
        return e;
    }

    @Override
    public <T> T findWithExistCheck(Class<T> clazz, Long id) {
        T t = em.find(clazz, id);
        if (t == null){
            throw new EntityNotFoundException(clazz.getSimpleName() + " with id " + id + " not found");
        }
        return t;
    }

    @Override
    public <T> void findWithExistCheck(Class<T> clazz, Long id, Consumer<T> consumer) {
        T t = em.find(clazz, id);
        if (t == null){
            throw new EntityNotFoundException(clazz.getSimpleName() + " with id " + id + " not found");
        }
        consumer.accept(t);
    }

    @Override
    public <T> void findWithExistCheck(Long id, Consumer<T> consumer) {
        E e = em.find(clazz, id);
        if (e == null){
            throw new EntityNotFoundException(clazz.getSimpleName() + " with id " + id + " not found");
        }
        consumer.accept((T) e);
    }
}
