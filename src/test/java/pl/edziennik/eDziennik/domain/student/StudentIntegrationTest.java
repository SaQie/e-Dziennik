package pl.edziennik.eDziennik.domain.student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import pl.edziennik.eDziennik.BaseTest;
import pl.edziennik.eDziennik.domain.school.domain.School;
import pl.edziennik.eDziennik.domain.schoolclass.SchoolClassIntergrationTestUtil;
import pl.edziennik.eDziennik.domain.schoolclass.domain.SchoolClass;
import pl.edziennik.eDziennik.domain.schoolclass.dto.SchoolClassRequestApiDto;
import pl.edziennik.eDziennik.domain.schoolclass.services.SchoolClassService;
import pl.edziennik.eDziennik.domain.settings.dto.SettingsValue;
import pl.edziennik.eDziennik.domain.settings.services.SettingsService;
import pl.edziennik.eDziennik.domain.student.domain.Student;
import pl.edziennik.eDziennik.domain.student.dto.StudentRequestApiDto;
import pl.edziennik.eDziennik.domain.student.dto.StudentResponseApiDto;
import pl.edziennik.eDziennik.domain.student.services.StudentService;
import pl.edziennik.eDziennik.domain.student.services.validator.StudentValidators;
import pl.edziennik.eDziennik.domain.subject.SubjectIntegrationTestUtil;
import pl.edziennik.eDziennik.domain.subject.dto.SubjectRequestApiDto;
import pl.edziennik.eDziennik.domain.subject.services.SubjectService;
import pl.edziennik.eDziennik.domain.teacher.TeacherIntegrationTestUtil;
import pl.edziennik.eDziennik.domain.teacher.dto.TeacherRequestApiDto;
import pl.edziennik.eDziennik.domain.teacher.services.TeacherService;
import pl.edziennik.eDziennik.server.exceptions.BusinessException;
import pl.edziennik.eDziennik.server.exceptions.EntityNotFoundException;

import javax.persistence.Query;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@Rollback
public class StudentIntegrationTest extends BaseTest {

    @Autowired
    private StudentService service;

    @Autowired
    private SettingsService settingsService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private SchoolClassService schoolClassService;

    private final StudentIntegrationTestUtil util;
    private final SubjectIntegrationTestUtil subjectUtil;
    private final SchoolClassIntergrationTestUtil schoolClassUtil;
    private final TeacherIntegrationTestUtil teacherUtil;


    public StudentIntegrationTest() {
        this.util = new StudentIntegrationTestUtil();
        this.subjectUtil = new SubjectIntegrationTestUtil();
        this.schoolClassUtil = new SchoolClassIntergrationTestUtil();
        this.teacherUtil = new TeacherIntegrationTestUtil();
    }

    @BeforeEach
    public void prepareDb() {
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
        assertEquals(expected.getUsername(), actual.getUser().getUsername());
    }

    @Test
    public void shouldUpdateStudent() {
        // given
        StudentRequestApiDto dto = util.prepareStudentRequestDto();
        Long id = service.register(dto).getId();
        StudentRequestApiDto expected = util.prepareStudentRequestDto("AfterEdit", "AfterEdit1", "AfterEdit2", "999999", "test2@example.com");

        // when
        Long updated = service.updateStudent(id, expected).getId();

        // then
        assertNotNull(updated);
        assertEquals(updated, id);
        Student actual = find(Student.class, updated);
        assertEquals(expected.getFirstName(), actual.getPersonInformation().getFirstName());
        assertEquals(expected.getLastName(), actual.getPersonInformation().getLastName());
        assertEquals(expected.getAddress(), actual.getAddress().getAddress());
        assertEquals(expected.getPesel(), actual.getPersonInformation().getPesel());
        assertEquals(expected.getCity(), actual.getAddress().getCity());
        assertEquals(expected.getUsername(), actual.getUser().getUsername());

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
        assertEquals(exception.getMessage(), resourceCreator.of("not.found.message", idStudent, Student.class.getSimpleName()));
    }

    @Test
    public void shouldFindListOfStudents() {
        // given
        StudentRequestApiDto firstStudent = util.prepareStudentRequestDto();
        StudentRequestApiDto secondStudent = util.prepareStudentRequestDto("Adam", "Nowako", "ASD", "12312322", "test2@example.com");
        Long firstStudentId = service.register(firstStudent).getId();
        Long secondStudentId = service.register(secondStudent).getId();
        assertNotNull(firstStudentId);
        assertNotNull(secondStudentId);

        // when
        int actual = service.findAllStudents(PageRequest.of(0, 20)).getContent().size();

        // then
        assertEquals(2, actual);
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
        assertEquals(expected.getAddress(), actual.getAddress());
        assertEquals(expected.getPesel(), actual.getPesel());
        assertEquals(expected.getCity(), actual.getCity());
    }

    @Test
    public void shouldThrowsExceptionWhenStudentDoesNotExist() {
        // given
        Long idStudent = 1L;

        // when
        Exception exception = assertThrows(EntityNotFoundException.class, () -> service.findStudentById(idStudent));

        // then
        assertEquals(exception.getMessage(), resourceCreator.of("not.found.message", idStudent, Student.class.getSimpleName()));
    }

    @Test
    public void shouldThrowsExceptionWhenTryingToDeleteNotExistingStudent() {
        // given
        Long idStudent = 1L;

        // when
        Exception exception = assertThrows(EntityNotFoundException.class, () -> service.deleteStudentById(idStudent));

        // then
        assertEquals(exception.getMessage(), resourceCreator.of("not.found.message", idStudent, Student.class.getSimpleName()));
    }

    @Test
    public void shouldThrowsExceptionWhenTryingSaveNewStudentIfSchoolNotExist() {
        // given
        Long idSchool = 15L;
        StudentRequestApiDto dto = util.prepareStudentRequestDto(idSchool);

        // when
        Exception exception = assertThrows(EntityNotFoundException.class, () -> service.register(dto));

        // then
        assertEquals(exception.getMessage(), resourceCreator.of("not.found.message", idSchool, School.class.getSimpleName()));

    }

    @Test
    public void shouldThrowsExceptionWhenTryingSaveNewStudentIfSchoolClassNotExist() {
        // given
        Long idSchool = 100L;
        Long idSchoolClass = 999L;
        StudentRequestApiDto dto = util.prepareStudentRequestDto(idSchool, idSchoolClass);

        // when
        Exception exception = assertThrows(EntityNotFoundException.class, () -> service.register(dto));

        // then
        assertEquals(exception.getMessage(), resourceCreator.of("not.found.message", idSchoolClass, SchoolClass.class.getSimpleName()));
    }

    @Test
    public void shouldThrowsBusinessExceptionWhenTryingToRegisterNewStudentAndSchoolClassNotBelongsToSchool() {
        // given
        StudentRequestApiDto dto = util.prepareStudentRequestDto(100L, 101L);

        // when
        BusinessException exception = assertThrows(BusinessException.class, () -> service.register(dto));

        // then
        assertEquals(1, exception.getErrors().size());
        assertEquals(StudentValidators.STUDENT_SCHOOL_CLASS_NOT_BELONGS_TO_SCHOOL_VALIDATOR_NAME, exception.getErrors().get(0).getErrorThrownedBy());
        assertEquals(StudentRequestApiDto.ID_SCHOOL_CLASS, exception.getErrors().get(0).getField());
        String expectedExceptionMessage = resourceCreator.of(StudentValidators.EXCEPTION_MESSAGE_SCHOOL_CLASS_NOT_BELONG_TO_SCHOOL, find(SchoolClass.class, dto.getIdSchoolClass()).getClassName(), find(School.class, dto.getIdSchool()).getName());
        assertEquals(expectedExceptionMessage, exception.getErrors().get(0).getCause());
    }

    @Test
    public void shouldThrowsBusinessExceptionWhenTryingToRegisterNewStudentAndPeselAlreadyExist() {
        // given
        StudentRequestApiDto dto = util.prepareStudentRequestDto("00000000000");
        StudentResponseApiDto register = service.register(dto);
        StudentRequestApiDto dto2 = util.prepareStudentRequestDto("xxx", "xxx", "xxxx", "00000000000", "test2@example.com");

        // when
        BusinessException exception = assertThrows(BusinessException.class, () -> service.register(dto2));

        // then
        assertEquals(1, exception.getErrors().size());
        assertEquals(StudentValidators.STUDENT_PESEL_NOT_UNIQUE_VALIDATOR_NAME, exception.getErrors().get(0).getErrorThrownedBy());
        assertEquals(StudentRequestApiDto.PESEL, exception.getErrors().get(0).getField());
        String expectedExceptionMessage = resourceCreator.of(StudentValidators.EXCEPTION_MESSAGE_PESEL_NOT_UNIQUE, dto.getPesel());
        assertEquals(expectedExceptionMessage, exception.getErrors().get(0).getCause());
    }

    @Test
    public void shouldAssignAllClassSubjectsToStudentWhenSettingIsEnabled() {
        // given
        settingsService.updateSetting(SettingsService.AUTOMATICALLY_INSERT_STUDENT_SUBJECTS_WHEN_ADD, new SettingsValue(true));

        SchoolClassRequestApiDto schoolClassRequestApiDto = schoolClassUtil.prepareSchoolClassRequest();
        Long idSchoolClass = schoolClassService.createSchoolClass(schoolClassRequestApiDto).getId();

        TeacherRequestApiDto teacherRequestApiDto = teacherUtil.prepareTeacherRequestDto();
        Long idTeacher = teacherService.register(teacherRequestApiDto).getId();

        SubjectRequestApiDto subjectRequestApiDto = subjectUtil.prepareSubjectRequestDto("first", idTeacher, idSchoolClass);
        Long firstIdSubject = subjectService.createNewSubject(subjectRequestApiDto).getId();

        SubjectRequestApiDto secondSubjectRequestApiDto = subjectUtil.prepareSubjectRequestDto("second", idTeacher, idSchoolClass);
        Long secondIdSubject = subjectService.createNewSubject(secondSubjectRequestApiDto).getId();

        SubjectRequestApiDto thirdSubjectRequestApiDto = subjectUtil.prepareSubjectRequestDto("third", idTeacher, idSchoolClass);
        Long thirdIdSubject = subjectService.createNewSubject(thirdSubjectRequestApiDto).getId();

        SubjectRequestApiDto fourthSubjectRequestApiDto = subjectUtil.prepareSubjectRequestDto("fourth", idTeacher, idSchoolClass);
        Long fourthIdSubject = subjectService.createNewSubject(fourthSubjectRequestApiDto).getId();

        StudentRequestApiDto dto = util.prepareStudentRequestDto("example", "wwww@o2.pl", idSchoolClass);
        // when
        StudentResponseApiDto responseApiDto = service.register(dto);

        // then
        List<Long> idsSubjects = getActualStudentSubjectsIdsList(responseApiDto.getId());
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
