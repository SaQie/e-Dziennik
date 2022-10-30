package pl.edziennik.eDziennik.server.basics;

import liquibase.pro.packaged.T;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public interface IBaseDao<E extends Serializable>{

    List<E> findAll();

    E saveOrUpdate(final E entity);

    Optional<E> find(final Long id);

    boolean exist(final Long id);

    <T> boolean exist(Class<T> clazz, final Long id);

    void remove(final E entity);

    void remove(final Long id);

    String getClazzName();

    EntityManager getEm();

    <T> Optional<T> find(Class<T> clazz, final Long id);

    <T> T get(Class<T> clazz, final Long id);
    E findWithExistCheck(final Long id);
    <T> void findWithExistCheck(final Long id, final Consumer<T> consumer);

    // Find by Found check?
    <T> T findWithExistCheck(Class<T> clazz,final Long id);
    <T> void findWithExistCheck(Class<T> clazz,final Long id, final Consumer<T> consumer);

}
