package pl.edziennik.eDziennik.subject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import pl.edziennik.eDziennik.BaseTest;
import pl.edziennik.eDziennik.exceptions.BusinessException;
import pl.edziennik.eDziennik.exceptions.EntityNotFoundException;
import pl.edziennik.eDziennik.server.basics.BaseDao;
import pl.edziennik.eDziennik.server.school.domain.School;
import pl.edziennik.eDziennik.server.school.domain.dto.SchoolRequestApiDto;
import pl.edziennik.eDziennik.server.school.services.validator.SchoolValidators;
import pl.edziennik.eDziennik.server.schoolclass.domain.SchoolClass;
import pl.edziennik.eDziennik.server.student.domain.Student;
import pl.edziennik.eDziennik.server.subject.domain.Subject;
import pl.edziennik.eDziennik.server.subject.domain.dto.SubjectRequestApiDto;
import pl.edziennik.eDziennik.server.subject.domain.dto.SubjectResponseApiDto;
import pl.edziennik.eDziennik.server.subject.services.SubjectService;
import pl.edziennik.eDziennik.server.subject.services.validator.SubjectValidators;
import pl.edziennik.eDziennik.server.teacher.domain.Teacher;
import pl.edziennik.eDziennik.server.teacher.domain.dto.TeacherRequestApiDto;
import pl.edziennik.eDziennik.server.teacher.services.TeacherService;
import pl.edziennik.eDziennik.teacher.TeacherIntegrationTestUtil;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
@Rollback
public class SubjectIntegrationTest extends BaseTest {

    @Autowired
    private SubjectService service;

    @Autowired
    private TeacherService teacherService;

    @BeforeEach
    public void prepareDb() {
        clearDb();
        fillDbWithData();
    }

    private final SubjectIntegrationTestUtil util;
    private final TeacherIntegrationTestUtil teacherUtil;

    public SubjectIntegrationTest() {
        this.util = new SubjectIntegrationTestUtil();
        this.teacherUtil = new TeacherIntegrationTestUtil();
    }

    @Test
    public void shouldSaveNewSubjectWithTeacher() {
        // given
        TeacherRequestApiDto dto = teacherUtil.prepareTeacherRequestDto();
        Long teacherId = teacherService.register(dto).getId();
        assertNotNull(teacherId);

        SubjectRequestApiDto expected = util.prepareSubjectRequestDto(teacherId);

        // when
        Long subjectId = service.createNewSubject(expected).getId();

        // then
        assertNotNull(subjectId);
        Subject actual = find(Subject.class, subjectId);
        assertNotNull(actual);

        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getIdTeacher(), actual.getTeacher().getId());
    }

    @Test
    public void shouldUpdateSubject() {
        // given
        TeacherRequestApiDto teacherDto = teacherUtil.prepareTeacherRequestDto();
        Long teacherId = teacherService.register(teacherDto).getId();
        assertNotNull(teacherId);

        SubjectRequestApiDto dto = util.prepareSubjectRequestDto(teacherId);
        Long subjectId = service.createNewSubject(dto).getId();
        SubjectRequestApiDto expected = util.prepareSubjectRequestDto("Chemia", teacherId);

        // when
        Long updated = service.updateSubject(subjectId, expected).getId();

        // then
        assertNotNull(updated);
        assertEquals(updated, subjectId);
        Subject actual = find(Subject.class, updated);
        assertNotNull(actual);

        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getIdTeacher(), actual.getTeacher().getId());
    }

    @Test
    public void shouldSaveNewSubjectWithoutTeacher() {
        // given
        SubjectRequestApiDto expected = util.prepareSubjectRequestDto(null);

        // when
        Long subjectId = service.createNewSubject(expected).getId();

        // then
        assertNotNull(subjectId);
        Subject actual = find(Subject.class, subjectId);
        assertNotNull(actual);

        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertNull(actual.getTeacher());
    }

    @Test
    public void shouldThrowsExceptionWhenSaveNewSubjectAndTeacherNotExist() {
        // given
        Long idTeacher = 222L;
        SubjectRequestApiDto expected = util.prepareSubjectRequestDto(idTeacher);

        // when
        Exception exception = assertThrows(EntityNotFoundException.class, () -> service.createNewSubject(expected));

        // then
        assertEquals(exception.getMessage(), resourceCreator.of("not.found.message", idTeacher, Teacher.class.getSimpleName()));
    }

    @Test
    public void shouldFindSubjectWithGivenId() {
        // given
        SubjectRequestApiDto expected = util.prepareSubjectRequestDto(null);
        Long idSubject = service.createNewSubject(expected).getId();
        assertNotNull(idSubject);

        // when
        SubjectResponseApiDto actual = service.findSubjectById(idSubject);

        // then
        assertNotNull(actual);
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertNull(actual.getTeacher());
    }

    @Test
    public void shouldFindListOfSubjects() {
        // given
        SubjectRequestApiDto firstSubject = util.prepareSubjectRequestDto(null);
        SubjectRequestApiDto secondSubject = util.prepareSubjectRequestDto("Chemia", null);

        Long firstSubjectId = service.createNewSubject(firstSubject).getId();
        assertNotNull(firstSubjectId);
        Long secondSubjectId = service.createNewSubject(secondSubject).getId();
        assertNotNull(secondSubjectId);

        // when
        List<SubjectResponseApiDto> actual = service.findAllSubjects();

        // then
        assertEquals(2, actual.size());

    }

    @Test
    public void shouldThrowsExceptionWhenTryingToSaveSubjectAndSubjectAlreadyExist() {
        // given
        SubjectRequestApiDto dto = util.prepareSubjectRequestDto(null);
        // first subject
        service.createNewSubject(dto);

        // second subject with the same name
        SubjectRequestApiDto dto2 = util.prepareSubjectRequestDto(null);
        // when
        BusinessException exception = assertThrows(BusinessException.class, () -> service.createNewSubject(dto2));

        // then
        assertEquals(1, exception.getErrors().size());
        assertEquals(SubjectValidators.SUBJECT_ALREADY_EXIST_VALIDATOR_NAME, exception.getErrors().get(0).getErrorThrownedBy());
        assertEquals(SubjectRequestApiDto.NAME, exception.getErrors().get(0).getField());
        String expectedExceptionMessage = resourceCreator.of(SubjectValidators.EXCEPTION_MESSAGE_SUBJECT_ALREADY_EXIST, dto.getName(), find(SchoolClass.class, dto.getIdSchoolClass()).getClassName());
        assertEquals(expectedExceptionMessage, exception.getErrors().get(0).getCause());


    }
}
