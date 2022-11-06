package pl.edziennik.eDziennik;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import pl.edziennik.eDziennik.server.basics.BaseDao;
import pl.edziennik.eDziennik.server.student.domain.Student;

import javax.sql.DataSource;
import java.util.List;

@ActiveProfiles("test")
@SpringBootTest
public class DaoTest extends BaseTest {

    @Autowired
    BaseDao<Student> dao;

    @BeforeEach
    public void prepareDb(){
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
        Assertions.assertNotNull(actualStudent);
        Assertions.assertEquals(expectedStudent, actualStudent);
    }

    @Test
    public void shouldDelete() {
        // given
        Student student = new Student();
        student.setFirstName("Test");
        student.setLastName("Testowy");
        dao.saveOrUpdate(student);
        int sizeAfterSave = dao.findAll().size();
        Assertions.assertEquals(1, sizeAfterSave);
        // when
        dao.remove(student);
        // then
        List<Student> expectedStudents = dao.findAll();
        Assertions.assertEquals(0, expectedStudents.size());
    }

    @Test
    public void shouldUpdate() {
        // given
        Student student = new Student();
        student.setFirstName("Test");
        student.setLastName("Testowy");
        Student savedStudent = dao.saveOrUpdate(student);
        Student studentAfterUpdate = find(Student.class, savedStudent.getId());
        Assertions.assertEquals(student.getFirstName(), studentAfterUpdate.getFirstName());
        Assertions.assertEquals(student.getLastName(), studentAfterUpdate.getLastName());
        // when
        studentAfterUpdate.setFirstName("After");
        // then
        Student studentAfterUpdate2 = dao.get(Student.class, studentAfterUpdate.getId());
        Assertions.assertEquals("After", studentAfterUpdate2.getFirstName());
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
        Assertions.assertNotNull(studentAfterFind);
        Assertions.assertEquals(student.getId(), studentAfterFind.getId());
        Assertions.assertEquals(student.getFirstName(), studentAfterFind.getFirstName());
        Assertions.assertEquals(student.getLastName(), studentAfterFind.getLastName());
    }

}
