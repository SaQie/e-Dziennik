package pl.edziennik.eDziennik.server.basics;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Basics interface for DAO
 *
 * @param <ENTITY>
 */
public interface IBaseDao<ENTITY extends AbstractEntity> {

    /**
     * This method return list of all objects without pagination
     */
    List<ENTITY> findAll();

    /**
     * This method return list of objects with pagination functionality
     * Throws BussinesException when page or size is less or equal zero
     */
    Page<List<ENTITY>> findAll(int page, int size);

    /**
     * This method saves new entity or update if entity already exist
     */
    ENTITY saveOrUpdate(final ENTITY entity);

    /**
     * This method saves all entities in list
     */
    List<ENTITY> saveAll(final List<ENTITY> entities);

    /**
     * This method return optional of finded entity
     */
    Optional<ENTITY> find(final Long id);

    /**
     * This method return an object if exists or throws EntityNotFoundException
     */
    ENTITY get(final Long id);

    /**
     * This method return true if object exist by id
     */
    boolean isExist(final Long id);


    /**
     * This method return true if object exist by id
     */
    <T> boolean isExist(Class<T> clazz, final Long id);

    /**
     * This method removes an entity
     */
    void remove(final ENTITY entity);

    /**
     * This method removes an entity by id
     */
    void remove(final Long id);

    /**
     * This method return class name passed as generic argument
     */
    String getClazzName();

    /**
     * This method returns entity manager instance
     */
    EntityManager getEm();

    /**
     * This method returns optional of finded object
     */
    <T> Optional<T> find(Class<T> clazz, final Long id);

    /**
     * Return object or throws EntityNotFoundException when not exist
     */
    <T> T get(Class<T> clazz, final Long id);

    /**
     * Execute consumer on found object or throws EntityNotFoundException if not exist
     */
    <T> void findWithExecute(final Long id, final Consumer<T> consumer);

    /**
     * Execute consumer on found object or throws EntityNotFoundException if not exist
     */
    <T> void findWithExecute(Class<T> clazz, final Long id, final Consumer<T> consumer);

}
