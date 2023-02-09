package pl.edziennik.eDziennik;

import static org.junit.jupiter.api.Assertions.*;

import liquibase.pro.packaged.P;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import pl.edziennik.eDziennik.exceptions.EntityNotFoundException;
import pl.edziennik.eDziennik.server.basics.BaseDao;
import pl.edziennik.eDziennik.server.personinformation.PersonInformation;
import pl.edziennik.eDziennik.server.school.domain.School;
import pl.edziennik.eDziennik.server.student.domain.Student;
import pl.edziennik.eDziennik.server.user.domain.User;

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
        PersonInformation personInformation = new PersonInformation();
        User user = new User();
        personInformation.setFirstName("Test");
        personInformation.setLastName("Testowy");
        user.setPersonInformation(personInformation);
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
        PersonInformation personInformation = new PersonInformation();
        User user = new User();
        personInformation.setFirstName("Test");
        personInformation.setLastName("Testowy");
        user.setPersonInformation(personInformation);
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
        personInformation.setFirstName("Test");
        personInformation.setLastName("Testowy");
        user.setPersonInformation(personInformation);
        User savedUser = dao.saveOrUpdate(user);
        User userAfterUpdate = find(User.class, savedUser.getId());
        assertEquals(personInformation.getFirstName(), userAfterUpdate.getPersonInformation().getFirstName());
        assertEquals(personInformation.getLastName(), userAfterUpdate.getPersonInformation().getLastName());
        // when
        PersonInformation personInformation2 = find(PersonInformation.class, personInformation.getId());
        personInformation2.setFirstName("After");
        // then
        User userAfterUpdate2 = dao.get(User.class, userAfterUpdate.getId());
        assertEquals("After", userAfterUpdate2.getPersonInformation().getFirstName());
    }

    @Test
    public void shouldFind() {
        // given
        PersonInformation personInformation = new PersonInformation();
        User user = new User();
        personInformation.setFirstName("Test");
        personInformation.setLastName("Testowy");
        user.setPersonInformation(personInformation);
        User savedUser = dao.saveOrUpdate(user);
        // when
        User userAfterFind = dao.get(User.class, savedUser.getId());
        // then
        assertNotNull(userAfterFind);
        assertEquals(user.getId(), userAfterFind.getId());
        assertEquals(user.getPersonInformation().getFirstName(), userAfterFind.getPersonInformation().getFirstName());
        assertEquals(user.getPersonInformation().getLastName(), userAfterFind.getPersonInformation().getLastName());
    }

    @Test
    public void shouldFindAll() {
        // given
        PersonInformation personInformation1 = new PersonInformation();
        User user = new User();
        personInformation1.setFirstName("Test");
        personInformation1.setLastName("Testowy");
        user.setPersonInformation(personInformation1);
        dao.saveOrUpdate(user);

        PersonInformation personInformation2 = new PersonInformation();
        User user2 = new User();
        personInformation2.setFirstName("Test2");
        personInformation2.setLastName("Testowy2");
        user2.setPersonInformation(personInformation2);
        dao.saveOrUpdate(user2);

        PersonInformation personInformation3 = new PersonInformation();
        User user3 = new User();
        personInformation3.setFirstName("Test3");
        personInformation3.setLastName("Testowy3");
        user3.setPersonInformation(personInformation3);
        dao.saveOrUpdate(user3);

        // when
        int actual = dao.findAll().size();

        // then
        assertEquals(3, actual);
    }

    @Test
    public void shouldSaveAll() {
        // given
        PersonInformation personInformation = new PersonInformation();
        User user = new User();
        personInformation.setFirstName("Test");
        personInformation.setLastName("Testowy");
        user.setPersonInformation(personInformation);

        PersonInformation personInformation2 = new PersonInformation();
        User user2 = new User();
        personInformation2.setFirstName("Test2");
        personInformation2.setLastName("Testowy2");
        user2.setPersonInformation(personInformation2);

        PersonInformation personInformation3 = new PersonInformation();
        User user3 = new User();
        personInformation3.setFirstName("Test3");
        personInformation3.setLastName("Testowy3");
        user3.setPersonInformation(personInformation3);

        // when
        dao.saveAll(List.of(user3, user2, user));

        // then
        int actual = dao.findAll().size();
        assertEquals(3, actual);

    }

    @Test
    public void shouldFindWithExecute() {
        // given
        String expectedNameAfterExecute = "AfterExecute";
        String expectedLastNameAfterExecute = "AfterExecute2";

        PersonInformation personInformation = new PersonInformation();
        User user = new User();
        personInformation.setFirstName("Test");
        personInformation.setLastName("Testowy");
        user.setPersonInformation(personInformation);
        Long savedId = dao.saveOrUpdate(user).getId();

        // when
        PersonInformation personInformation2 = new PersonInformation();
        personInformation2.setFirstName(expectedNameAfterExecute);
        personInformation2.setLastName(expectedLastNameAfterExecute);
        dao.findWithExecute(User.class, savedId, savedStudent -> {
            savedStudent.setPersonInformation(personInformation2);
        });

        // then
        User savedUser = dao.get(User.class, savedId);
        assertNotNull(savedUser);
        assertEquals(user.getId(), savedUser.getId());
        assertEquals(expectedNameAfterExecute, savedUser.getPersonInformation().getFirstName());
        assertEquals(   expectedLastNameAfterExecute, savedUser.getPersonInformation().getLastName());
    }

    @Test
    public void shouldThrowsExceptionWhenTryingToFindWithExecuteAndEntityNotExist() {
        // given
        Long idStudent = 999L;
        // when
        PersonInformation personInformation = new PersonInformation();
        personInformation.setFirstName("a");
        personInformation.setLastName("b");
        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> dao.findWithExecute(Student.class, idStudent,
                        savedStudent -> savedStudent.getUser().setPersonInformation(personInformation)));
        // then
        assertEquals(exception.getMessage(), resourceCreator.of("not.found.message", idStudent, Student.class.getSimpleName()));
    }

}
