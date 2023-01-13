package pl.edziennik.eDziennik;

import static org.junit.jupiter.api.Assertions.*;

import liquibase.pro.packaged.P;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import pl.edziennik.eDziennik.exceptions.EntityNotFoundException;
import pl.edziennik.eDziennik.server.basics.BaseDao;
import pl.edziennik.eDziennik.server.personinformation.PersonInformation;
import pl.edziennik.eDziennik.server.student.domain.Student;

import java.util.List;

@ActiveProfiles("test")
@SpringBootTest
public class DaoTest extends BaseTest {

    @Autowired
    BaseDao<Student> dao;

    @BeforeEach
    public void prepareDb() {
        clearDb();
        fillDbWithData();
    }

    @Test
    public void shouldInsert() {
        // given
        PersonInformation personInformation = new PersonInformation();
        Student expectedStudent = new Student();
        personInformation.setFirstName("Test");
        personInformation.setLastName("Testowy");
        expectedStudent.setPersonInformation(personInformation);
        // when
        Student savedStudent = dao.saveOrUpdate(expectedStudent);
        // then
        Student actualStudent = dao.get(Student.class, savedStudent.getId());
        assertNotNull(actualStudent);
        assertEquals(expectedStudent, actualStudent);
    }

    @Test
    public void shouldDelete() {
        // given
        PersonInformation personInformation = new PersonInformation();
        Student student = new Student();
        personInformation.setFirstName("Test");
        personInformation.setLastName("Testowy");
        student.setPersonInformation(personInformation);
        dao.saveOrUpdate(student);
        int sizeAfterSave = dao.findAll().size();
        assertEquals(1, sizeAfterSave);
        // when
        dao.remove(student);
        // then
        List<Student> expectedStudents = dao.findAll();
        assertEquals(0, expectedStudents.size());
    }

    @Test
    public void shouldUpdate() {
        // given
        PersonInformation personInformation = new PersonInformation();
        Student student = new Student();
        personInformation.setFirstName("Test");
        personInformation.setLastName("Testowy");
        student.setPersonInformation(personInformation);
        Student savedStudent = dao.saveOrUpdate(student);
        Student studentAfterUpdate = find(Student.class, savedStudent.getId());
        assertEquals(personInformation.getFirstName(), studentAfterUpdate.getPersonInformation().getFirstName());
        assertEquals(personInformation.getLastName(), studentAfterUpdate.getPersonInformation().getLastName());
        // when
        PersonInformation personInformation2 = find(PersonInformation.class, personInformation.getId());
        personInformation2.setFirstName("After");
        // then
        Student studentAfterUpdate2 = dao.get(Student.class, studentAfterUpdate.getId());
        assertEquals("After", studentAfterUpdate2.getPersonInformation().getFirstName());
    }

    @Test
    public void shouldFind() {
        // given
        PersonInformation personInformation = new PersonInformation();
        Student student = new Student();
        personInformation.setFirstName("Test");
        personInformation.setLastName("Testowy");
        student.setPersonInformation(personInformation);
        Student savedStudent = dao.saveOrUpdate(student);
        // when
        Student studentAfterFind = dao.get(Student.class, savedStudent.getId());
        // then
        assertNotNull(studentAfterFind);
        assertEquals(student.getId(), studentAfterFind.getId());
        assertEquals(student.getPersonInformation().getFirstName(), studentAfterFind.getPersonInformation().getFirstName());
        assertEquals(student.getPersonInformation().getLastName(), studentAfterFind.getPersonInformation().getLastName());
    }

    @Test
    public void shouldFindAll() {
        // given
        PersonInformation personInformation1 = new PersonInformation();
        Student student = new Student();
        personInformation1.setFirstName("Test");
        personInformation1.setLastName("Testowy");
        student.setPersonInformation(personInformation1);
        dao.saveOrUpdate(student);

        PersonInformation personInformation2 = new PersonInformation();
        Student student2 = new Student();
        personInformation2.setFirstName("Test2");
        personInformation2.setLastName("Testowy2");
        student.setPersonInformation(personInformation2);
        dao.saveOrUpdate(student2);

        PersonInformation personInformation3 = new PersonInformation();
        Student student3 = new Student();
        personInformation3.setFirstName("Test3");
        personInformation3.setLastName("Testowy3");
        student.setPersonInformation(personInformation3);
        dao.saveOrUpdate(student3);

        // when
        int actual = dao.findAll().size();

        // then
        assertEquals(3, actual);
    }

    @Test
    public void shouldSaveAll() {
        // given
        PersonInformation personInformation = new PersonInformation();
        Student student = new Student();
        personInformation.setFirstName("Test");
        personInformation.setLastName("Testowy");
        student.setPersonInformation(personInformation);

        PersonInformation personInformation2 = new PersonInformation();
        Student student2 = new Student();
        personInformation2.setFirstName("Test2");
        personInformation2.setLastName("Testowy2");
        student2.setPersonInformation(personInformation2);

        PersonInformation personInformation3 = new PersonInformation();
        Student student3 = new Student();
        personInformation3.setFirstName("Test3");
        personInformation3.setLastName("Testowy3");
        student3.setPersonInformation(personInformation3);

        // when
        dao.saveAll(List.of(student, student2, student3));

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
        Student student = new Student();
        personInformation.setFirstName("Test");
        personInformation.setLastName("Testowy");
        student.setPersonInformation(personInformation);
        Long savedId = dao.saveOrUpdate(student).getId();

        // when
        PersonInformation personInformation2 = new PersonInformation();
        personInformation2.setFirstName(expectedNameAfterExecute);
        personInformation2.setLastName(expectedLastNameAfterExecute);
        dao.findWithExecute(Student.class, savedId, savedStudent -> {
            savedStudent.setPersonInformation(personInformation2);
        });

        // then
        Student savedStudent = dao.get(Student.class, savedId);
        assertNotNull(savedStudent);
        assertEquals(student.getId(), savedStudent.getId());
        assertEquals(expectedNameAfterExecute, savedStudent.getPersonInformation().getFirstName());
        assertEquals(   expectedLastNameAfterExecute, savedStudent.getPersonInformation().getLastName());
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
                        savedStudent -> savedStudent.setPersonInformation(personInformation)));
        // then
        assertEquals(exception.getMessage(), resourceCreator.of("not.found.message", idStudent, Student.class.getSimpleName()));
    }

}
