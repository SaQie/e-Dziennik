package pl.edziennik.eDziennik.server.basics;

import java.io.Serializable;
import java.util.List;

public interface IBaseService<E> {

    List<E> findAll();

    E saveOrUpdate(E entity);

    E find(final Long id);

    void remove(final E entity);

}
