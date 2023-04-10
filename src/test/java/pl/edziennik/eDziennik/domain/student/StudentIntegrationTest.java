package pl.edziennik.eDziennik.domain.student;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import pl.edziennik.eDziennik.BaseTesting;
import pl.edziennik.eDziennik.domain.school.domain.School;
import pl.edziennik.eDziennik.domain.schoolclass.domain.SchoolClass;
import pl.edziennik.eDziennik.domain.schoolclass.dto.SchoolClassRequestApiDto;
import pl.edziennik.eDziennik.domain.settings.dto.SettingsValue;
import pl.edziennik.eDziennik.domain.settings.services.SettingsService;
import pl.edziennik.eDziennik.domain.student.domain.Student;
import pl.edziennik.eDziennik.domain.student.dto.StudentRequestApiDto;
import pl.edziennik.eDziennik.domain.student.dto.StudentResponseApiDto;
import pl.edziennik.eDziennik.domain.student.services.validator.StudentValidators;
import pl.edziennik.eDziennik.domain.subject.dto.SubjectRequestApiDto;
import pl.edziennik.eDziennik.domain.teacher.dto.TeacherRequestApiDto;
import pl.edziennik.eDziennik.server.exceptions.BusinessException;
import pl.edziennik.eDziennik.server.exceptions.EntityNotFoundException;

import javax.persistence.Query;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@Rollback
public class StudentIntegrationTest extends BaseTesting {


    @Test
    public void shouldSaveNewStudent() {
        // given
        StudentRequestApiDto expected = studentUtil.prepareStudentRequestDto();

        // when
        Long id = studentService.register(expected).id();

        // then
        assertNotNull(id);
        Student actual = find(Student.class, id);
        assertEquals(expected.firstName(), actual.getPersonInformation().getFirstName());
        assertEquals(expected.lastName(), actual.getPersonInformation().getLastName());
        assertEquals(expected.address(), actual.getAddress().getAddress());
        assertEquals(expected.pesel(), actual.getPersonInformation().getPesel());
        assertEquals(expected.city(), actual.getAddress().getCity());
        assertEquals(expected.username(), actual.getUser().getUsername());
    }

    @Test
    public void shouldUpdateStudent() {
        // given
        StudentRequestApiDto dto = studentUtil.prepareStudentRequestDto();
        Long id = studentService.register(dto).id();
        StudentRequestApiDto expected = studentUtil.prepareStudentRequestDto("AfterEdit", "AfterEdit1", "AfterEdit2", "999999", "test2@example.com");

        // when
        Long updated = studentService.updateStudent(id, expected).id();

        // then
        assertNotNull(updated);
        assertEquals(updated, id);
        Student actual = find(Student.class, updated);
        assertEquals(expected.firstName(), actual.getPersonInformation().getFirstName());
        assertEquals(expected.lastName(), actual.getPersonInformation().getLastName());
        assertEquals(expected.address(), actual.getAddress().getAddress());
        assertEquals(expected.pesel(), actual.getPersonInformation().getPesel());
        assertEquals(expected.city(), actual.getAddress().getCity());
        assertEquals(expected.username(), actual.getUser().getUsername());

    }

    @Test
    public void shouldThrowsExceptionWhenUpdateAndSchoolNotExists(){
        // given
        StudentRequestApiDto dto = studentUtil.prepareStudentRequestDto();
        Long id = studentService.register(dto).id();
        StudentRequestApiDto expected = studentUtil.prepareStudentRequestDto(9999L, 999L);

        // when

        // then
        Exception exception = assertThrows(EntityNotFoundException.class, () -> studentService.updateStudent(id, expected));
        assertEquals(exception.getMessage(), resourceCreator.of("not.found.message", expected.idSchool(), School.class.getSimpleName()));
    }

    @Test
    public void shouldThrowsExceptionWhenUpdateAndSchoolClassNotExists(){
        // given
        StudentRequestApiDto dto = studentUtil.prepareStudentRequestDto();
        Long id = studentService.register(dto).id();
        StudentRequestApiDto expected = studentUtil.prepareStudentRequestDto(100L, 999L);

        // when

        // then
        Exception exception = assertThrows(EntityNotFoundException.class, () -> studentService.updateStudent(id, expected));
        assertEquals(exception.getMessage(), resourceCreator.of("not.found.message", expected.idSchoolClass(), SchoolClass.class.getSimpleName()));
    }

    @Test
    public void shouldDeleteStudent() {
        // given
        StudentRequestApiDto dto = studentUtil.prepareStudentRequestDto();
        Long idStudent = studentService.register(dto).id();
        assertNotNull(idStudent);

        // when
        studentService.deleteStudentById(idStudent);

        // then
        Exception exception = assertThrows(EntityNotFoundException.class, () -> studentService.findStudentById(idStudent));
        assertEquals(exception.getMessage(), resourceCreator.of("not.found.message", idStudent, Student.class.getSimpleName()));
    }

    @Test
    public void shouldFindListOfStudents() {
        // given
        StudentRequestApiDto firstStudent = studentUtil.prepareStudentRequestDto();
        StudentRequestApiDto secondStudent = studentUtil.prepareStudentRequestDto("Adam", "Nowako", "ASD", "12312322", "test2@example.com");
        Long firstStudentId = studentService.register(firstStudent).id();
        Long secondStudentId = studentService.register(secondStudent).id();
        assertNotNull(firstStudentId);
        assertNotNull(secondStudentId);

        // when
        int actual = studentService.findAllStudents(PageRequest.of(0, 20)).getContent().size();

        // then
        assertEquals(2, actual);
    }

    @Test
    public void shouldFindStudentWithGivenId() {
        // given
        StudentRequestApiDto expected = studentUtil.prepareStudentRequestDto();
        Long id = studentService.register(expected).id();

        // when
        StudentResponseApiDto actual = studentService.findStudentById(id);

        // then
        assertNotNull(actual);
        assertEquals(expected.firstName(), actual.firstName());
        assertEquals(expected.lastName(), actual.lastName());
        assertEquals(expected.address(), actual.address());
        assertEquals(expected.pesel(), actual.pesel());
        assertEquals(expected.city(), actual.city());
    }

    @Test
    public void shouldThrowsExceptionWhenStudentDoesNotExist() {
        // given
        Long idStudent = 1L;

        // when
        Exception exception = assertThrows(EntityNotFoundException.class, () -> studentService.findStudentById(idStudent));

        // then
        assertEquals(exception.getMessage(), resourceCreator.of("not.found.message", idStudent, Student.class.getSimpleName()));
    }

    @Test
    public void shouldThrowsExceptionWhenTryingToDeleteNotExistingStudent() {
        // given
        Long idStudent = 1L;

        // when
        Exception exception = assertThrows(EntityNotFoundException.class, () -> studentService.deleteStudentById(idStudent));

        // then
        assertEquals(exception.getMessage(), resourceCreator.of("not.found.message", idStudent, Student.class.getSimpleName()));
    }

    @Test
    public void shouldThrowsExceptionWhenTryingSaveNewStudentIfSchoolNotExist() {
        // given
        Long idSchool = 15L;
        StudentRequestApiDto dto = studentUtil.prepareStudentRequestDto(idSchool);

        // when
        Exception exception = assertThrows(EntityNotFoundException.class, () -> studentService.register(dto));

        // then
        assertEquals(exception.getMessage(), resourceCreator.of("not.found.message", idSchool, School.class.getSimpleName()));

    }

    @Test
    public void shouldThrowsExceptionWhenTryingSaveNewStudentIfSchoolClassNotExist() {
        // given
        Long idSchool = 100L;
        Long idSchoolClass = 999L;
        StudentRequestApiDto dto = studentUtil.prepareStudentRequestDto(idSchool, idSchoolClass);

        // when
        Exception exception = assertThrows(EntityNotFoundException.class, () -> studentService.register(dto));

        // then
        assertEquals(exception.getMessage(), resourceCreator.of("not.found.message", idSchoolClass, SchoolClass.class.getSimpleName()));
    }

    @Test
    public void shouldThrowsBusinessExceptionWhenTryingToRegisterNewStudentAndSchoolClassNotBelongsToSchool() {
        // given
        StudentRequestApiDto dto = studentUtil.prepareStudentRequestDto(100L, 101L);

        // when
        BusinessException exception = assertThrows(BusinessException.class, () -> studentService.register(dto));

        // then
        assertEquals(1, exception.getErrors().size());
        assertEquals(StudentValidators.STUDENT_SCHOOL_CLASS_NOT_BELONGS_TO_SCHOOL_VALIDATOR_NAME, exception.getErrors().get(0).getErrorThrownedBy());
        assertEquals(StudentRequestApiDto.ID_SCHOOL_CLASS, exception.getErrors().get(0).getField());
        String expectedExceptionMessage = resourceCreator.of(StudentValidators.EXCEPTION_MESSAGE_SCHOOL_CLASS_NOT_BELONG_TO_SCHOOL, find(SchoolClass.class, dto.idSchoolClass()).getClassName(), find(School.class, dto.idSchool()).getName());
        assertEquals(expectedExceptionMessage, exception.getErrors().get(0).getCause());
    }

    @Test
    public void shouldThrowsBusinessExceptionWhenTryingToRegisterNewStudentAndPeselAlreadyExist() {
        // given
        StudentRequestApiDto dto = studentUtil.prepareStudentRequestDto("00000000000");
        StudentResponseApiDto register = studentService.register(dto);
        StudentRequestApiDto dto2 = studentUtil.prepareStudentRequestDto("xxx", "xxx", "xxxx", "00000000000", "test2@example.com");

        // when
        BusinessException exception = assertThrows(BusinessException.class, () -> studentService.register(dto2));

        // then
        assertEquals(1, exception.getErrors().size());
        assertEquals(StudentValidators.STUDENT_PESEL_NOT_UNIQUE_VALIDATOR_NAME, exception.getErrors().get(0).getErrorThrownedBy());
        assertEquals(StudentRequestApiDto.PESEL, exception.getErrors().get(0).getField());
        String expectedExceptionMessage = resourceCreator.of(StudentValidators.EXCEPTION_MESSAGE_PESEL_NOT_UNIQUE, dto.pesel());
        assertEquals(expectedExceptionMessage, exception.getErrors().get(0).getCause());
    }

    @Test
    public void shouldAssignAllClassSubjectsToStudentWhenSettingIsEnabled() {
        // given
        settingsService.updateSetting(SettingsService.AUTOMATICALLY_INSERT_STUDENT_SUBJECTS_WHEN_ADD, new SettingsValue(true, null, null));

        SchoolClassRequestApiDto schoolClassRequestApiDto = schoolClassUtil.prepareSchoolClassRequest();
        Long idSchoolClass = schoolClassService.createSchoolClass(schoolClassRequestApiDto).id();

        TeacherRequestApiDto teacherRequestApiDto = teacherUtil.prepareTeacherRequestDto();
        Long idTeacher = teacherService.register(teacherRequestApiDto).id();

        SubjectRequestApiDto subjectRequestApiDto = subjectUtil.prepareSubjectRequestDto("first", idTeacher, idSchoolClass);
        Long firstIdSubject = subjectService.createNewSubject(subjectRequestApiDto).id();

        SubjectRequestApiDto secondSubjectRequestApiDto = subjectUtil.prepareSubjectRequestDto("second", idTeacher, idSchoolClass);
        Long secondIdSubject = subjectService.createNewSubject(secondSubjectRequestApiDto).id();

        SubjectRequestApiDto thirdSubjectRequestApiDto = subjectUtil.prepareSubjectRequestDto("third", idTeacher, idSchoolClass);
        Long thirdIdSubject = subjectService.createNewSubject(thirdSubjectRequestApiDto).id();

        SubjectRequestApiDto fourthSubjectRequestApiDto = subjectUtil.prepareSubjectRequestDto("fourth", idTeacher, idSchoolClass);
        Long fourthIdSubject = subjectService.createNewSubject(fourthSubjectRequestApiDto).id();

        StudentRequestApiDto dto = studentUtil.prepareStudentRequestDto("example", "wwww@o2.pl", idSchoolClass);
        // when
        StudentResponseApiDto responseApiDto = studentService.register(dto);

        // then
        List<Long> idsSubjects = getActualStudentSubjectsIdsList(responseApiDto.id());
        assertEquals(idsSubjects.size(), 4);
        assertTrue(idsSubjects.containsAll(List.of(firstIdSubject, secondIdSubject, thirdIdSubject, fourthIdSubject)));
    }

    /**
     * This method return all subjects assigned to student
     *
     * @param idStudent
     * @return
     */
    private List<Long> getActualStudentSubjectsIdsList(Long idStudent) {
        Query query = em.createQuery("select s.subject.id from StudentSubject s where s.student.id = :idStudent");
        query.setParameter("idStudent", idStudent);
        return query.getResultList();
    }


}
