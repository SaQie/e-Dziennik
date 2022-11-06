package pl.edziennik.eDziennik;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.sql.SQLException;


/**
 * Basics class for test
 */
@Transactional
@SpringBootTest
public class BaseTest {

    @Autowired
    private DataSource dataSource;


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


    protected void fillDbWithData(){
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator(false, false, "UTF-8", new ClassPathResource("/db/data.sql"));
        populator.execute(dataSource);
    }

    protected void clearDb(){
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator(false, false, "UTF-8", new ClassPathResource("/db/clearData.sql"));
        populator.execute(dataSource);
    }

    protected final Long ROLE_ADMIN = 1L;
    protected final Long ROLE_MODERATOR = 2L;
    protected final Long ROLE_TEACHER = 3L;

}
