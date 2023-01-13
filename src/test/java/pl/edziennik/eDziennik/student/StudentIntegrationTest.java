package pl.edziennik.eDziennik.student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import pl.edziennik.eDziennik.BaseTest;
import pl.edziennik.eDziennik.exceptions.BusinessException;
import pl.edziennik.eDziennik.exceptions.EntityNotFoundException;
import pl.edziennik.eDziennik.server.basics.BaseDao;
import pl.edziennik.eDziennik.server.school.domain.School;
import pl.edziennik.eDziennik.server.schoolclass.domain.SchoolClass;
import pl.edziennik.eDziennik.server.student.domain.Student;
import pl.edziennik.eDziennik.server.student.domain.dto.StudentRequestApiDto;
import pl.edziennik.eDziennik.server.student.domain.dto.StudentResponseApiDto;
import pl.edziennik.eDziennik.server.student.services.StudentService;
import pl.edziennik.eDziennik.server.student.services.validator.StudentValidators;

import javax.persistence.EntityTransaction;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

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
        assertEquals(expected.getFirstName(), actual.getPersonInformation().getFirstName());
        assertEquals(expected.getLastName(), actual.getPersonInformation().getLastName());
        assertEquals(expected.getAddress(), actual.getAddress().getAddress());
        assertEquals(expected.getPesel(), actual.getPersonInformation().getPesel());
        assertEquals(expected.getCity(), actual.getAddress().getCity());
        assertEquals(expected.getUsername(), actual.getUsername());
        assertEquals(expected.getParentFirstName(), actual.getParentFirstName());
        assertEquals(expected.getParentPhoneNumber(), actual.getParentPhoneNumber());
        assertEquals(expected.getParentLastName(), actual.getParentLastName());
    }

    @Test
    public void shouldUpdateStudent(){
        // given
        StudentRequestApiDto dto = util.prepareStudentRequestDto();
        Long id = service.register(dto).getId();
        StudentRequestApiDto expected = util.prepareStudentRequestDto("AfterEdit", "AfterEdit1", "AfterEdit2", "999999");

        // when
        Long updated = service.updateStudent(id, expected).getId();

        // then
        assertNotNull(updated);
        assertEquals(updated,id);
        Student actual = find(Student.class, updated);
        assertEquals(expected.getFirstName(), actual.getPersonInformation().getFirstName());
        assertEquals(expected.getLastName(), actual.getPersonInformation().getLastName());
        assertEquals(expected.getAddress(), actual.getAddress().getAddress());
        assertEquals(expected.getPesel(), actual.getPersonInformation().getPesel());
        assertEquals(expected.getCity(), actual.getAddress().getCity());
        assertEquals(expected.getUsername(), actual.getUsername());
        assertEquals(expected.getParentFirstName(), actual.getParentFirstName());
        assertEquals(expected.getParentPhoneNumber(), actual.getParentPhoneNumber());
        assertEquals(expected.getParentLastName(), actual.getParentLastName());

    }

    @Test
    public void shouldDeleteStudent() {
        // given
        StudentRequestApiDto dto = util.prepareStudentRequestDto();
        Long idStudent = service.register(dto).getId();
        assertNotNull(idStudent);

        // when
        service.deleteStudentById(idStudent);

        // then
        Exception exception = assertThrows(EntityNotFoundException.class, () -> service.findStudentById(idStudent));
        assertEquals(exception.getMessage(), resourceCreator.of("not.found.message", idStudent,Student.class.getSimpleName()));
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
        assertEquals(expected.getAddress(), actual.getAdress());
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
        Exception exception = assertThrows(pl.edziennik.eDziennik.exceptions.EntityNotFoundException.class, () -> service.findStudentById(idStudent));

        // then
        assertEquals(exception.getMessage(), resourceCreator.of("not.found.message", idStudent,Student.class.getSimpleName()));
    }

    @Test
    public void shouldThrowsExceptionWhenTryingToDeleteNotExistingStudent() {
        // given
        Long idStudent = 1L;

        // when
        Exception exception = assertThrows(pl.edziennik.eDziennik.exceptions.EntityNotFoundException.class, () -> service.deleteStudentById(idStudent));

        // then
        assertEquals(exception.getMessage(), resourceCreator.of("not.found.message", idStudent,Student.class.getSimpleName()));
    }

    @Test
    public void shouldThrowsExceptionWhenTryingSaveNewStudentIfSchoolNotExist() {
        // given
        Long idSchool = 15L;
        StudentRequestApiDto dto = util.prepareStudentRequestDto(idSchool);

        // when
        Exception exception = assertThrows(pl.edziennik.eDziennik.exceptions.EntityNotFoundException.class, () -> service.register(dto));

        // then
        assertEquals(exception.getMessage(), resourceCreator.of("not.found.message", idSchool,School.class.getSimpleName()));

    }

    @Test
    public void shouldThrowsExceptionWhenTryingSaveNewStudentIfSchoolClassNotExist(){
        // given
        Long idSchool = 100L;
        Long idSchoolClass = 999L;
        StudentRequestApiDto dto = util.prepareStudentRequestDto(idSchool, idSchoolClass);

        // when
        Exception exception = assertThrows(EntityNotFoundException.class, () -> service.register(dto));

        // then
        assertEquals(exception.getMessage(), resourceCreator.of("not.found.message", idSchoolClass,SchoolClass.class.getSimpleName()));
    }

    @Test
    public void shouldThrowsBusinessExceptionWhenTryingToRegisterAndStudentAlreadyExist(){
        // given
        String expectedValidatorName = "StudentAlreadyExistValidator";
        StudentRequestApiDto dto = util.prepareStudentRequestDto();
        service.register(dto);
        StudentRequestApiDto dto2 = util.prepareStudentRequestDto("Test123", "asdasd", "asdasd", "1231232322");

        // when
        BusinessException exception = assertThrows(BusinessException.class, () -> service.register(dto2));

        // then
        assertEquals(1, exception.getErrors().size());
        assertEquals(expectedValidatorName, exception.getErrors().get(0).getErrorThrownedBy());
        assertEquals(List.of(StudentRequestApiDto.USERNAME), exception.getErrors().get(0).getFields());
        String expectedExceptionMessage = resourceCreator.of(StudentValidators.EXCEPTION_MESSAGE_STUDENT_ALREADY_EXIST, dto.getUsername());
        assertEquals(expectedExceptionMessage, exception.getErrors().get(0).getCause());
    }

    @Test
    public void shouldThrowsBusinessExceptionWhenTryingToRegisterNewStudentAndSchoolClassNotBelongsToSchool(){
        // given
        String expectedValidatorName = "StudentSchoolClassNotBelongsToSchoolValidator";
        StudentRequestApiDto dto = util.prepareStudentRequestDto(100L,101L);

        // when
        BusinessException exception = assertThrows(BusinessException.class, () -> service.register(dto));

        // then
        assertEquals(1, exception.getErrors().size());
        assertEquals(expectedValidatorName, exception.getErrors().get(0).getErrorThrownedBy());
        assertEquals(List.of(StudentRequestApiDto.ID_SCHOOL_CLASS), exception.getErrors().get(0).getFields());
        String expectedExceptionMessage = resourceCreator.of(StudentValidators.EXCEPTION_MESSAGE_SCHOOL_CLASS_NOT_BELONG_TO_SCHOOL, find(SchoolClass.class, dto.getIdSchoolClass()).getClassName(),find(School.class, dto.getIdSchool()).getName());
        assertEquals(expectedExceptionMessage, exception.getErrors().get(0).getCause());
    }

    @Test
    public void shouldThrowsBusinessExceptionWhenTryingToRegisterNewStudentAndPeselAlreadyExist(){
        // given
        String expectedValidatorName = "StudentPeselNotUniqueValidator";
        StudentRequestApiDto dto = util.prepareStudentRequestDto("00000000000");
        service.register(dto);
        StudentRequestApiDto dto2 = util.prepareStudentRequestDto("xxx", "xxx", "xxxx", "00000000000");

        // when
        BusinessException exception = assertThrows(BusinessException.class, () -> service.register(dto2));

        // then
        assertEquals(1, exception.getErrors().size());
        assertEquals(expectedValidatorName, exception.getErrors().get(0).getErrorThrownedBy());
        assertEquals(List.of(StudentRequestApiDto.PESEL), exception.getErrors().get(0).getFields());
        String expectedExceptionMessage = resourceCreator.of(StudentValidators.EXCEPTION_MESSAGE_PESEL_NOT_UNIQUE, Student.class.getSimpleName(), dto.getPesel());
        assertEquals(expectedExceptionMessage, exception.getErrors().get(0).getCause());
    }


}
