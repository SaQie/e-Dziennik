package pl.edziennik.eDziennik;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import pl.edziennik.eDziennik.exceptions.EntityNotFoundException;
import pl.edziennik.eDziennik.server.basics.BaseDao;
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
        Student expectedStudent = new Student();
        expectedStudent.setFirstName("Test");
        expectedStudent.setLastName("Testowy");
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
        Student student = new Student();
        student.setFirstName("Test");
        student.setLastName("Testowy");
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
        Student student = new Student();
        student.setFirstName("Test");
        student.setLastName("Testowy");
        Student savedStudent = dao.saveOrUpdate(student);
        Student studentAfterUpdate = find(Student.class, savedStudent.getId());
        assertEquals(student.getFirstName(), studentAfterUpdate.getFirstName());
        assertEquals(student.getLastName(), studentAfterUpdate.getLastName());
        // when
        studentAfterUpdate.setFirstName("After");
        // then
        Student studentAfterUpdate2 = dao.get(Student.class, studentAfterUpdate.getId());
        assertEquals("After", studentAfterUpdate2.getFirstName());
    }

    @Test
    public void shouldFind() {
        // given
        Student student = new Student();
        student.setFirstName("Test");
        student.setLastName("Testowy");
        Student savedStudent = dao.saveOrUpdate(student);
        // when
        Student studentAfterFind = dao.get(Student.class, savedStudent.getId());
        // then
        assertNotNull(studentAfterFind);
        assertEquals(student.getId(), studentAfterFind.getId());
        assertEquals(student.getFirstName(), studentAfterFind.getFirstName());
        assertEquals(student.getLastName(), studentAfterFind.getLastName());
    }

    @Test
    public void shouldFindAll() {
        // given
        Student student = new Student();
        student.setFirstName("Test");
        student.setLastName("Testowy");
        dao.saveOrUpdate(student);

        Student student2 = new Student();
        student2.setFirstName("Test2");
        student2.setLastName("Testowy2");
        dao.saveOrUpdate(student2);

        Student student3 = new Student();
        student.setFirstName("Test3");
        student.setLastName("Testowy3");
        dao.saveOrUpdate(student3);

        // when
        int actual = dao.findAll().size();

        // then
        assertEquals(3, actual);
    }

    @Test
    public void shouldSaveAll() {
        // given
        Student student = new Student();
        student.setFirstName("Test");
        student.setLastName("Testowy");

        Student student2 = new Student();
        student2.setFirstName("Test2");
        student2.setLastName("Testowy2");

        Student student3 = new Student();
        student.setFirstName("Test3");
        student.setLastName("Testowy3");

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

        Student student = new Student();
        student.setFirstName("Test");
        student.setLastName("Testowy");
        Long savedId = dao.saveOrUpdate(student).getId();

        // when
        dao.findWithExecute(Student.class, savedId, savedStudent -> {
            savedStudent.setFirstName(expectedNameAfterExecute);
            savedStudent.setLastName(expectedLastNameAfterExecute);
        });

        // then
        Student savedStudent = dao.get(Student.class, savedId);
        assertNotNull(savedStudent);
        assertEquals(student.getId(), savedStudent.getId());
        assertEquals(expectedNameAfterExecute, savedStudent.getFirstName());
        assertEquals(expectedLastNameAfterExecute, savedStudent.getLastName());
    }

    @Test
    public void shouldThrowsExceptionWhenTryingToFindWithExecuteAndEntityNotExist() {
        // given
        Long idStudent = 999L;
        // when
        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> dao.findWithExecute(Student.class, idStudent,
                        savedStudent -> savedStudent.setLastName("xxx")));
        // then
        assertEquals(exception.getMessage(), BaseDao.BaseDaoExceptionMessage.createNotFoundExceptionMessage(Student.class.getSimpleName(),idStudent));
    }

}
