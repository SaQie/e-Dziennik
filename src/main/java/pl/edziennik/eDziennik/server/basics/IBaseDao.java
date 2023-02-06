package pl.edziennik.eDziennik.server.basics;

import liquibase.pro.packaged.T;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public interface IBaseDao<E extends AbstractEntity>{

    List<E> findAll();

    Page<List<E>> findAll(int page, int size);

    E saveOrUpdate(final E entity);

    List<E> saveAll(final List<E> entities);

    Optional<E> find(final Long id);

    E get(final Long id);

    boolean isExist(final Long id);

    <T> boolean isExist(Class<T> clazz, final Long id);

    void remove(final E entity);

    void remove(final Long id);

    String getClazzName();

    EntityManager getEm();

    <T> Optional<T> find(Class<T> clazz, final Long id);

    /**
     * Return object or throws EntityNotFoundException when not exist
     *
     * @param clazz
     * @param id
     * @param <T>
     * @return
     */
    <T> T get(Class<T> clazz, final Long id);

    /**
     * Execute consumer on found object or throws EntityNotFoundException if not exist
     *
     * @param id
     * @param consumer
     * @param <T>
     */
    <T> void findWithExecute(final Long id, final Consumer<T> consumer);


    /**
     * Execute consumer on found object or throws EntityNotFoundException if not exist
     *
     * @param clazz
     * @param id
     * @param consumer
     * @param <T>
     */
    <T> void findWithExecute(Class<T> clazz,final Long id, final Consumer<T> consumer);

}
