package pl.edziennik.eDziennik.domain.subject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import pl.edziennik.eDziennik.BaseTest;
import pl.edziennik.eDziennik.server.basics.dto.PageRequest;
import pl.edziennik.eDziennik.server.exceptions.BusinessException;
import pl.edziennik.eDziennik.server.exceptions.EntityNotFoundException;
import pl.edziennik.eDziennik.domain.schoolclass.domain.SchoolClass;
import pl.edziennik.eDziennik.domain.subject.domain.Subject;
import pl.edziennik.eDziennik.domain.subject.dto.SubjectRequestApiDto;
import pl.edziennik.eDziennik.domain.subject.dto.SubjectResponseApiDto;
import pl.edziennik.eDziennik.domain.subject.services.SubjectService;
import pl.edziennik.eDziennik.domain.subject.services.validator.SubjectValidators;
import pl.edziennik.eDziennik.domain.teacher.domain.Teacher;
import pl.edziennik.eDziennik.domain.teacher.dto.TeacherRequestApiDto;
import pl.edziennik.eDziennik.domain.teacher.services.TeacherService;
import pl.edziennik.eDziennik.domain.teacher.TeacherIntegrationTestUtil;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
@Rollback
public class SubjectIntegrationTest extends BaseTest {

    @Test
    public void shouldSaveNewSubjectWithTeacher() {
        // given
        TeacherRequestApiDto dto = teacherUtil.prepareTeacherRequestDto();
        Long teacherId = teacherService.register(dto).getId();
        assertNotNull(teacherId);

        SubjectRequestApiDto expected = subjectUtil.prepareSubjectRequestDto(teacherId);

        // when
        Long subjectId = subjectService.createNewSubject(expected).getId();

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

        SubjectRequestApiDto dto = subjectUtil.prepareSubjectRequestDto(teacherId);
        Long subjectId = subjectService.createNewSubject(dto).getId();
        SubjectRequestApiDto expected = subjectUtil.prepareSubjectRequestDto("Chemia", teacherId);

        // when
        Long updated = subjectService.updateSubject(subjectId, expected).getId();

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
        SubjectRequestApiDto expected = subjectUtil.prepareSubjectRequestDto(null);

        // when
        Long subjectId = subjectService.createNewSubject(expected).getId();

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
        SubjectRequestApiDto expected = subjectUtil.prepareSubjectRequestDto(idTeacher);

        // when
        Exception exception = assertThrows(EntityNotFoundException.class, () -> subjectService.createNewSubject(expected));

        // then
        assertEquals(exception.getMessage(), resourceCreator.of("not.found.message", idTeacher, Teacher.class.getSimpleName()));
    }

    @Test
    public void shouldFindSubjectWithGivenId() {
        // given
        SubjectRequestApiDto expected = subjectUtil.prepareSubjectRequestDto(null);
        Long idSubject = subjectService.createNewSubject(expected).getId();
        assertNotNull(idSubject);

        // when
        SubjectResponseApiDto actual = subjectService.findSubjectById(idSubject);

        // then
        assertNotNull(actual);
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertNull(actual.getTeacher());
    }

    @Test
    public void shouldFindListOfSubjects() {
        // given
        SubjectRequestApiDto firstSubject = subjectUtil.prepareSubjectRequestDto(null);
        SubjectRequestApiDto secondSubject = subjectUtil.prepareSubjectRequestDto("Chemia", null);

        Long firstSubjectId = subjectService.createNewSubject(firstSubject).getId();
        assertNotNull(firstSubjectId);
        Long secondSubjectId = subjectService.createNewSubject(secondSubject).getId();
        assertNotNull(secondSubjectId);

        // when
        List<SubjectResponseApiDto> actual = subjectService.findAllSubjects(new PageRequest(1, 10)).getEntities();

        // then
        assertEquals(2, actual.size());

    }

    @Test
    public void shouldThrowsExceptionWhenTryingToSaveSubjectAndSubjectAlreadyExist() {
        // given
        SubjectRequestApiDto dto = subjectUtil.prepareSubjectRequestDto(null);
        // first subject
        subjectService.createNewSubject(dto);

        // second subject with the same name
        SubjectRequestApiDto dto2 = subjectUtil.prepareSubjectRequestDto(null);
        // when
        BusinessException exception = assertThrows(BusinessException.class, () -> subjectService.createNewSubject(dto2));

        // then
        assertEquals(1, exception.getErrors().size());
        assertEquals(SubjectValidators.SUBJECT_ALREADY_EXIST_VALIDATOR_NAME, exception.getErrors().get(0).getErrorThrownedBy());
        assertEquals(SubjectRequestApiDto.NAME, exception.getErrors().get(0).getField());
        String expectedExceptionMessage = resourceCreator.of(SubjectValidators.EXCEPTION_MESSAGE_SUBJECT_ALREADY_EXIST, dto.getName(), find(SchoolClass.class, dto.getIdSchoolClass()).getClassName());
        assertEquals(expectedExceptionMessage, exception.getErrors().get(0).getCause());


    }
}
