package pl.edziennik.eDziennik;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.eDziennik.domain.settings.services.SettingsService;
import pl.edziennik.eDziennik.server.utils.ResourceCreator;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;


/**
 * Basics class for test
 */
@Transactional
@SpringBootTest
public class BaseTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    protected ResourceCreator resourceCreator;


    @PersistenceContext
    protected EntityManager em;

    @Autowired
    private SettingsService service;

    /**
     * This method returns object or null in managed state in test persistance context
     *
     * @param clazz
     * @param id
     * @param <T>
     * @return
     */
    public  <T> T find(Class<T> clazz, Long id) {
        return em.find(clazz,id);
    }


    protected void fillDbWithData(){
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator(false, false, "UTF-8", new ClassPathResource("/db/data.sql"));
        populator.execute(dataSource);
        service.refreshCache();
    }

    protected void clearDb(){
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator(false, false, "UTF-8", new ClassPathResource("/db/clearData.sql"));
        populator.execute(dataSource);
    }

    protected final Long ROLE_ADMIN = 1L;
    protected final Long ROLE_MODERATOR = 2L;
    protected final Long ROLE_TEACHER = 3L;

    protected final String ROLE_ADMIN_TEXT = "ROLE_ADMIN";
    protected final String ROLE_MODERATOR_TEXT = "ROLE_MODERATOR";
    protected final String ROLE_TEACHER_TEXT = "ROLE_TEACHER";

}
