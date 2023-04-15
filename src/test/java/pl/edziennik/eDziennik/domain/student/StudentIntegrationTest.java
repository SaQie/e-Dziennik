package pl.edziennik.eDziennik.domain.student;

import jakarta.persistence.Query;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import pl.edziennik.eDziennik.BaseTesting;
import pl.edziennik.eDziennik.domain.school.domain.School;
import pl.edziennik.eDziennik.domain.school.domain.wrapper.SchoolId;
import pl.edziennik.eDziennik.domain.schoolclass.domain.SchoolClass;
import pl.edziennik.eDziennik.domain.schoolclass.domain.wrapper.SchoolClassId;
import pl.edziennik.eDziennik.domain.schoolclass.dto.SchoolClassRequestApiDto;
import pl.edziennik.eDziennik.domain.settings.dto.SettingsValue;
import pl.edziennik.eDziennik.domain.settings.services.SettingsService;
import pl.edziennik.eDziennik.domain.student.domain.Student;
import pl.edziennik.eDziennik.domain.student.domain.wrapper.StudentId;
import pl.edziennik.eDziennik.domain.student.dto.StudentRequestApiDto;
import pl.edziennik.eDziennik.domain.student.dto.StudentResponseApiDto;
import pl.edziennik.eDziennik.domain.student.services.validator.StudentValidators;
import pl.edziennik.eDziennik.domain.subject.domain.wrapper.SubjectId;
import pl.edziennik.eDziennik.domain.subject.dto.SubjectRequestApiDto;
import pl.edziennik.eDziennik.domain.teacher.domain.wrapper.TeacherId;
import pl.edziennik.eDziennik.domain.teacher.dto.TeacherRequestApiDto;
import pl.edziennik.eDziennik.server.exceptions.BusinessException;
import pl.edziennik.eDziennik.server.exceptions.EntityNotFoundException;

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
        StudentId id = studentService.register(expected).studentId();

        // then
        assertNotNull(id);
        Student actual = find(Student.class, id);
        assertEquals(expected.firstName(), actual.getPersonInformation().firstName());
        assertEquals(expected.lastName(), actual.getPersonInformation().lastName());
        assertEquals(expected.address(), actual.getAddress().getAddress());
        assertEquals(expected.pesel(), actual.getPersonInformation().pesel().value());
        assertEquals(expected.city(), actual.getAddress().getCity());
        assertEquals(expected.username(), actual.getUser().getUsername());
    }

    @Test
    public void shouldUpdateStudent() {
        // given
        StudentRequestApiDto dto = studentUtil.prepareStudentRequestDto();
        StudentId id = studentService.register(dto).studentId();
        StudentRequestApiDto expected = studentUtil.prepareStudentRequestDto("AfterEdit", "AfterEdit1", "AfterEdit2", "999999", "test2@example.com");

        // when
        StudentId updated = studentService.updateStudent(id, expected).studentId();

        // then
        assertNotNull(updated);
        assertEquals(updated, id);
        Student actual = find(Student.class, updated);
        assertEquals(expected.firstName(), actual.getPersonInformation().firstName());
        assertEquals(expected.lastName(), actual.getPersonInformation().lastName());
        assertEquals(expected.address(), actual.getAddress().getAddress());
        assertEquals(expected.pesel(), actual.getPersonInformation().pesel().value());
        assertEquals(expected.city(), actual.getAddress().getCity());
        assertEquals(expected.username(), actual.getUser().getUsername());

    }

    @Test
    public void shouldThrowsExceptionWhenUpdateAndSchoolNotExists(){
        // given
        StudentRequestApiDto dto = studentUtil.prepareStudentRequestDto();
        StudentId id = studentService.register(dto).studentId();
        StudentRequestApiDto expected = studentUtil.prepareStudentRequestDto(SchoolId.wrap(999L), SchoolClassId.wrap(999L));

        // when

        // then
        Exception exception = assertThrows(EntityNotFoundException.class, () -> studentService.updateStudent(id, expected));
        assertEquals(exception.getMessage(), resourceCreator.of("not.found.message", expected.schoolId().id(), School.class.getSimpleName()));
    }

    @Test
    public void shouldThrowsExceptionWhenUpdateAndSchoolClassNotExists(){
        // given
        StudentRequestApiDto dto = studentUtil.prepareStudentRequestDto();
        StudentId id = studentService.register(dto).studentId();
        StudentRequestApiDto expected = studentUtil.prepareStudentRequestDto(SchoolId.wrap(100L), SchoolClassId.wrap(999L));

        // when

        // then
        Exception exception = assertThrows(EntityNotFoundException.class, () -> studentService.updateStudent(id, expected));
        assertEquals(exception.getMessage(), resourceCreator.of("not.found.message", expected.schoolClassId().id(), SchoolClass.class.getSimpleName()));
    }

    @Test
    public void shouldDeleteStudent() {
        // given
        StudentRequestApiDto dto = studentUtil.prepareStudentRequestDto();
        StudentId idStudent = studentService.register(dto).studentId();
        assertNotNull(idStudent);

        // when
        studentService.deleteStudentById(idStudent);

        // then
        Exception exception = assertThrows(EntityNotFoundException.class, () -> studentService.findStudentById(idStudent));
        assertEquals(exception.getMessage(), resourceCreator.of("not.found.message", idStudent.id(), Student.class.getSimpleName()));
    }

    @Test
    public void shouldFindListOfStudents() {
        // given
        StudentRequestApiDto firstStudent = studentUtil.prepareStudentRequestDto();
        StudentRequestApiDto secondStudent = studentUtil.prepareStudentRequestDto("Adam", "Nowako", "ASD", "12312322", "test2@example.com");
        StudentId firstStudentId = studentService.register(firstStudent).studentId();
        StudentId secondStudentId = studentService.register(secondStudent).studentId();
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
        StudentId id = studentService.register(expected).studentId();

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
        StudentId studentId = StudentId.wrap(1L);

        // when
        Exception exception = assertThrows(EntityNotFoundException.class, () -> studentService.findStudentById(studentId));

        // then
        assertEquals(exception.getMessage(), resourceCreator.of("not.found.message", studentId.id(), Student.class.getSimpleName()));
    }

    @Test
    public void shouldThrowsExceptionWhenTryingToDeleteNotExistingStudent() {
        // given
        StudentId studentId = StudentId.wrap(1L);

        // when
        Exception exception = assertThrows(EntityNotFoundException.class, () -> studentService.deleteStudentById(studentId));

        // then
        assertEquals(exception.getMessage(), resourceCreator.of("not.found.message", studentId.id(), Student.class.getSimpleName()));
    }

    @Test
    public void shouldThrowsExceptionWhenTryingSaveNewStudentIfSchoolNotExist() {
        // given
        SchoolId schoolId = SchoolId.wrap(15L);
        StudentRequestApiDto dto = studentUtil.prepareStudentRequestDto(schoolId);

        // when
        Exception exception = assertThrows(EntityNotFoundException.class, () -> studentService.register(dto));

        // then
        assertEquals(exception.getMessage(), resourceCreator.of("not.found.message", schoolId.id(), School.class.getSimpleName()));

    }

    @Test
    public void shouldThrowsExceptionWhenTryingSaveNewStudentIfSchoolClassNotExist() {
        // given
        SchoolId schoolId = SchoolId.wrap(100L);
        SchoolClassId schoolClassId = SchoolClassId.wrap(999L);
        StudentRequestApiDto dto = studentUtil.prepareStudentRequestDto(schoolId, schoolClassId);

        // when
        Exception exception = assertThrows(EntityNotFoundException.class, () -> studentService.register(dto));

        // then
        assertEquals(exception.getMessage(), resourceCreator.of("not.found.message", schoolClassId.id(), SchoolClass.class.getSimpleName()));
    }

    @Test
    public void shouldThrowsBusinessExceptionWhenTryingToRegisterNewStudentAndSchoolClassNotBelongsToSchool() {
        // given
        SchoolId schoolId = SchoolId.wrap(100L);
        SchoolClassId schoolClassId = SchoolClassId.wrap(101L);
        StudentRequestApiDto dto = studentUtil.prepareStudentRequestDto(schoolId, schoolClassId);

        // when
        BusinessException exception = assertThrows(BusinessException.class, () -> studentService.register(dto));

        // then
        assertEquals(1, exception.getErrors().size());
        assertEquals(StudentValidators.STUDENT_SCHOOL_CLASS_NOT_BELONGS_TO_SCHOOL_VALIDATOR_NAME, exception.getErrors().get(0).getErrorThrownedBy());
        assertEquals(StudentRequestApiDto.ID_SCHOOL_CLASS, exception.getErrors().get(0).getField());
        String expectedExceptionMessage = resourceCreator.of(StudentValidators.EXCEPTION_MESSAGE_SCHOOL_CLASS_NOT_BELONG_TO_SCHOOL, find(SchoolClass.class, dto.schoolClassId()).getClassName(), find(School.class, dto.schoolId()).getName());
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
        SchoolClassId idSchoolClass = schoolClassService.createSchoolClass(schoolClassRequestApiDto).schoolClassId();

        TeacherRequestApiDto teacherRequestApiDto = teacherUtil.prepareTeacherRequestDto();
        TeacherId idTeacher = teacherService.register(teacherRequestApiDto).teacherId();

        SubjectRequestApiDto subjectRequestApiDto = subjectUtil.prepareSubjectRequestDto("first", idTeacher, idSchoolClass);
        SubjectId firstIdSubject = subjectService.createNewSubject(subjectRequestApiDto).subjectId();

        SubjectRequestApiDto secondSubjectRequestApiDto = subjectUtil.prepareSubjectRequestDto("second", idTeacher, idSchoolClass);
        SubjectId secondIdSubject = subjectService.createNewSubject(secondSubjectRequestApiDto).subjectId();

        SubjectRequestApiDto thirdSubjectRequestApiDto = subjectUtil.prepareSubjectRequestDto("third", idTeacher, idSchoolClass);
        SubjectId thirdIdSubject = subjectService.createNewSubject(thirdSubjectRequestApiDto).subjectId();

        SubjectRequestApiDto fourthSubjectRequestApiDto = subjectUtil.prepareSubjectRequestDto("fourth", idTeacher, idSchoolClass);
        SubjectId fourthIdSubject = subjectService.createNewSubject(fourthSubjectRequestApiDto).subjectId();

        StudentRequestApiDto dto = studentUtil.prepareStudentRequestDto("example", "wwww@o2.pl", idSchoolClass);
        // when
        StudentResponseApiDto responseApiDto = studentService.register(dto);

        // then
        List<Long> idsSubjects = getActualStudentSubjectsIdsList(responseApiDto.studentId().id());
        assertEquals(idsSubjects.size(), 4);
        assertTrue(idsSubjects.containsAll(List.of(firstIdSubject.id(), secondIdSubject.id(), thirdIdSubject.id(), fourthIdSubject.id())));
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
