package pl.edziennik.eDziennik;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import pl.edziennik.eDziennik.domain.address.domain.Address;
import pl.edziennik.eDziennik.server.exceptions.EntityNotFoundException;
import pl.edziennik.eDziennik.server.basics.dao.BaseDao;
import pl.edziennik.eDziennik.domain.personinformation.domain.PersonInformation;
import pl.edziennik.eDziennik.domain.student.domain.Student;
import pl.edziennik.eDziennik.domain.user.domain.User;

import java.util.List;

@ActiveProfiles("test")
@SpringBootTest
public class DaoTest extends BaseTest {

    @Autowired
    @Qualifier("userDaoImpl")
    BaseDao<User> dao;

    @BeforeEach
    public void prepareDb() {
        clearDb();
        fillDbWithData();
    }

    @Test
    public void shouldInsert() {
        // given
        User user = new User();
        // when
        User savedUser = dao.saveOrUpdate(user);
        // then
        User actualUser = dao.get(User.class, savedUser.getId());
        assertNotNull(actualUser);
        assertEquals(user, actualUser);
    }

    @Test
    public void shouldDelete() {
        // given
        User user = new User();
        ;
        dao.saveOrUpdate(user);
        int sizeAfterSave = dao.findAll().size();
        assertEquals(1, sizeAfterSave);
        // when
        dao.remove(user);
        // then
        List<User> expectedUsers = dao.findAll();
        assertEquals(0, expectedUsers.size());
    }

    @Test
    public void shouldUpdate() {
        // given
        PersonInformation personInformation = new PersonInformation();
        User user = new User();
        user.setUsername("asdasd");
        user.setPassword("cxzxc");
        user.setEmail("test@o2.pl");
        User savedUser = dao.saveOrUpdate(user);
        savedUser.setEmail("afterEdit@o2.pl");
        // when
        savedUser = dao.saveOrUpdate(savedUser);

        // then
        User userAfterUpdate = dao.get(User.class, savedUser.getId());
        assertEquals("afterEdit@o2.pl", userAfterUpdate.getEmail());
    }

    @Test
    public void shouldFind() {
        // given
        User user = new User();
        User savedUser = dao.saveOrUpdate(user);
        // when
        User userAfterFind = dao.get(User.class, savedUser.getId());
        // then
        assertNotNull(userAfterFind);
        assertEquals(user.getId(), userAfterFind.getId());
    }

    @Test
    public void shouldFindAll() {
        // given
        User user = new User();
        user.setUsername("first");
        dao.saveOrUpdate(user);

        User user2 = new User();
        user2.setUsername("second");
        dao.saveOrUpdate(user2);

        User user3 = new User();
        user3.setUsername("third");
        dao.saveOrUpdate(user3);

        // when
        int actual = dao.findAll().size();

        // then
        assertEquals(3, actual);
    }

    @Test
    public void shouldSaveAll() {
        // given
        User user = new User();

        User user2 = new User();

        User user3 = new User();

        // when
        dao.saveAll(List.of(user3, user2, user));

        // then
        int actual = dao.findAll().size();
        assertEquals(3, actual);

    }

    @Test
    public void shouldFindWithExecute() {
        // given
        String expectedNameAfterExecute = "something";

        User user = new User();
        Long savedId = dao.saveOrUpdate(user).getId();

        // when
        dao.findWithExecute(User.class, savedId, savedUser -> {
            savedUser.setUsername(expectedNameAfterExecute);
        });

        // then
        User savedUser = dao.get(User.class, savedId);
        assertNotNull(savedUser);
        assertEquals(user.getId(), savedUser.getId());
        assertEquals(expectedNameAfterExecute, expectedNameAfterExecute);
    }

    @Test
    public void shouldThrowsExceptionWhenTryingToFindWithExecuteAndEntityNotExist() {
        // given
        Long idStudent = 999L;
        // when
        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> dao.findWithExecute(Student.class, idStudent,
                        savedStudent -> savedStudent.setAddress(new Address())));
        // then
        assertEquals(exception.getMessage(), resourceCreator.of("not.found.message", idStudent, Student.class.getSimpleName()));
    }

}
