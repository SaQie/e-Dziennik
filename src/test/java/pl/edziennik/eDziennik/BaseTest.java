package pl.edziennik.eDziennik;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


/**
 * Basics class for test
 */
@Transactional
public class BaseTest {

    @PersistenceContext
    private EntityManager em;


    /**
     * This method returns object or null in managed state in test persistance context
     *
     * @param clazz
     * @param id
     * @param <T>
     * @return
     */
    protected  <T> T find(Class<T> clazz, Long id) {
        return em.find(clazz,id);
    }

}
