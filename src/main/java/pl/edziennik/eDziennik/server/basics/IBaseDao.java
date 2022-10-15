package pl.edziennik.eDziennik.server.basics;

import liquibase.pro.packaged.T;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface IBaseDao<E>{

    List<E> findAll();

    E saveOrUpdate(E entity);

    Optional<E> find(final Long id);

    void remove(final E entity);

    String getClazzName();

    EntityManager getEm();

    <T> Optional<T> find(Class<T> clazz, final Long id);
}
