package pl.edziennik.eDziennik.student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import pl.edziennik.eDziennik.BaseTest;
import pl.edziennik.eDziennik.server.student.domain.Student;
import pl.edziennik.eDziennik.server.student.domain.dto.StudentRequestApiDto;
import pl.edziennik.eDziennik.server.student.domain.dto.StudentResponseApiDto;
import pl.edziennik.eDziennik.server.student.services.StudentService;

import javax.persistence.EntityNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@Rollback
public class StudentIntegrationTest extends BaseTest {

    @Autowired
    private StudentService service;

    private final StudentIntegrationTestUtil util;


    public StudentIntegrationTest() {
        this.util = new StudentIntegrationTestUtil();
    }

    @BeforeEach
    void prepareDb() {
        clearDb();
        fillDbWithData();
    }

    @Test
    public void shouldSaveNewStudent() {
        // given
        StudentRequestApiDto expected = util.prepareStudentRequestDto();

        // when
        Long id = service.register(expected).getId();

        // then
        assertNotNull(id);
        Student actual = find(Student.class, id);
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getAdress(), actual.getAdress());
        assertEquals(expected.getPesel(), actual.getPESEL());
        assertEquals(expected.getCity(), actual.getCity());
        assertEquals(expected.getUsername(), actual.getUsername());
        assertEquals(expected.getParentFirstName(), actual.getParentFirstName());
        assertEquals(expected.getParentPhoneNumber(), actual.getParentPhoneNumber());
        assertEquals(expected.getParentLastName(), actual.getParentLastName());
    }

    @Test
    public void shouldDeleteStudent() {
        // given
        StudentRequestApiDto dto = util.prepareStudentRequestDto();
        Long id = service.register(dto).getId();
        assertNotNull(id);

        // when
        service.deleteStudentById(id);

        // then
        assertThrows(EntityNotFoundException.class, () -> service.findStudentById(id));
    }

    @Test
    public void shouldFindListOfStudents(){
        // given
        StudentRequestApiDto firstStudent = util.prepareStudentRequestDto();
        StudentRequestApiDto secondStudent = util.prepareStudentRequestDto("Adam", "Nowako", "ASD", "12312322");
        Long firstStudentId = service.register(firstStudent).getId();
        Long secondStudentId = service.register(secondStudent).getId();
        assertNotNull(firstStudentId);
        assertNotNull(secondStudentId);

        // when
        int actual = service.findAllStudents().size();

        // then
        assertEquals(2,actual);
    }

    @Test
    public void shouldFindStudentWithGivenId() {
        // given
        StudentRequestApiDto expected = util.prepareStudentRequestDto();
        Long id = service.register(expected).getId();

        // when
        StudentResponseApiDto actual = service.findStudentById(id);

        // then
        assertNotNull(actual);
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getAdress(), actual.getAdress());
        assertEquals(expected.getPesel(), actual.getPesel());
        assertEquals(expected.getCity(), actual.getCity());
        assertEquals(expected.getParentFirstName(), actual.getParentFirstName());
        assertEquals(expected.getParentPhoneNumber(), actual.getParentPhoneNumber());
        assertEquals(expected.getParentLastName(), actual.getParentLastName());
    }

    @Test
    public void shouldThrowsExceptionWhenStudentDoesNotExist() {
        // given
        Long idStudent = 1L;

        // when
        Throwable throwable = catchThrowable(() -> service.findStudentById(idStudent));

        // then
        assertThat(throwable).hasMessage("Student with given id " + idStudent+ " not exist");
    }

    @Test
    public void shouldThrowsExceptionWhenTryingToDeleteNotExistingStudent() {
        // given
        Long idStudent = 1L;

        // when
        Throwable throwable = catchThrowable(() -> service.deleteStudentById(idStudent));

        // then
        assertThat(throwable).hasMessage("Student with given id " + idStudent+ " not exist");
    }

    @Test
    public void shouldThrowsExceptionWhenTryingSaveNewStudentIfSchoolNotExist() {
        // given
        Long idSchool = 15L;
        StudentRequestApiDto dto = util.prepareStudentRequestDto(idSchool);

        // when
        Throwable throwable = catchThrowable(() -> service.register(dto));

        // then
        assertThat(throwable).hasMessageContaining("School with id " + idSchool + " not found");

    }

    @Test
    public void shouldThrowsExceptionWhenTryingSaveNewStudentIfSchoolClassNotExist(){
        // given
        Long idSchool = 1L;
        Long idSchoolClass = 999L;
        StudentRequestApiDto dto = util.prepareStudentRequestDto(idSchool, idSchoolClass);

        // when
        Throwable throwable = catchThrowable(() -> service.register(dto));

        // then
        assertThat(throwable).hasMessageContaining("SchoolClass with id " + idSchoolClass + " not found");
    }


}
