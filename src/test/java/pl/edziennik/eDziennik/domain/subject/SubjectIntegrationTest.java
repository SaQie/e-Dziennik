package pl.edziennik.eDziennik.domain.subject;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import pl.edziennik.eDziennik.BaseTesting;
import pl.edziennik.eDziennik.domain.schoolclass.domain.SchoolClass;
import pl.edziennik.eDziennik.domain.subject.domain.Subject;
import pl.edziennik.eDziennik.domain.subject.domain.wrapper.SubjectId;
import pl.edziennik.eDziennik.domain.subject.dto.SubjectRequestApiDto;
import pl.edziennik.eDziennik.domain.subject.dto.SubjectResponseApiDto;
import pl.edziennik.eDziennik.domain.subject.services.validator.SubjectValidators;
import pl.edziennik.eDziennik.domain.teacher.domain.Teacher;
import pl.edziennik.eDziennik.domain.teacher.domain.wrapper.TeacherId;
import pl.edziennik.eDziennik.domain.teacher.dto.TeacherRequestApiDto;
import pl.edziennik.eDziennik.server.basic.page.PageDto;
import pl.edziennik.eDziennik.server.exception.BusinessException;
import pl.edziennik.eDziennik.server.exception.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@Rollback
public class SubjectIntegrationTest extends BaseTesting {

    @Test
    public void shouldSaveNewSubjectWithTeacher() {
        // given
        TeacherRequestApiDto dto = teacherUtil.prepareTeacherRequestDto();
        TeacherId teacherId = teacherService.register(dto).teacherId();
        assertNotNull(teacherId);

        SubjectRequestApiDto expected = subjectUtil.prepareSubjectRequestDto(teacherId);

        // when
        SubjectId subjectId = subjectService.createNewSubject(expected).subjectId();

        // then
        assertNotNull(subjectId);
        Subject actual = find(Subject.class, subjectId);
        assertNotNull(actual);

        assertEquals(expected.name(), actual.getName());
        assertEquals(expected.description(), actual.getDescription());
        assertEquals(expected.teacherId(), actual.getTeacher().getTeacherId());
    }

    @Test
    public void shouldUpdateSubject() {
        // given
        TeacherRequestApiDto teacherDto = teacherUtil.prepareTeacherRequestDto();
        TeacherId teacherId = teacherService.register(teacherDto).teacherId();
        assertNotNull(teacherId);

        SubjectRequestApiDto dto = subjectUtil.prepareSubjectRequestDto(teacherId);
        SubjectId subjectId = subjectService.createNewSubject(dto).subjectId();
        SubjectRequestApiDto expected = subjectUtil.prepareSubjectRequestDto("Chemia", teacherId);

        // when
        SubjectId updated = subjectService.updateSubject(subjectId, expected).subjectId();

        // then
        assertNotNull(updated);
        assertEquals(updated, subjectId);
        Subject actual = find(Subject.class, updated);
        assertNotNull(actual);

        assertEquals(expected.name(), actual.getName());
        assertEquals(expected.description(), actual.getDescription());
        assertEquals(expected.teacherId(), actual.getTeacher().getTeacherId());
    }

    @Test
    public void shouldSaveNewSubjectWithoutTeacher() {
        // given
        SubjectRequestApiDto expected = subjectUtil.prepareSubjectRequestDto(null);

        // when
        SubjectId subjectId = subjectService.createNewSubject(expected).subjectId();

        // then
        assertNotNull(subjectId);
        Subject actual = find(Subject.class, subjectId);
        assertNotNull(actual);

        assertEquals(expected.name(), actual.getName());
        assertEquals(expected.description(), actual.getDescription());
        assertNull(actual.getTeacher());
    }

    @Test
    public void shouldThrowsExceptionWhenSaveNewSubjectAndTeacherNotExist() {
        // given
        TeacherId teacherId = TeacherId.wrap(222L);
        SubjectRequestApiDto expected = subjectUtil.prepareSubjectRequestDto(teacherId);

        // when
        Exception exception = assertThrows(EntityNotFoundException.class, () -> subjectService.createNewSubject(expected));

        // then
        assertEquals(exception.getMessage(), resourceCreator.of("not.found.message", teacherId.id(), Teacher.class.getSimpleName()));
    }

    @Test
    public void shouldFindSubjectWithGivenId() {
        // given
        SubjectRequestApiDto expected = subjectUtil.prepareSubjectRequestDto(null);
        SubjectId subjectId = subjectService.createNewSubject(expected).subjectId();
        assertNotNull(subjectId);

        // when
        SubjectResponseApiDto actual = subjectService.findSubjectById(subjectId);

        // then
        assertNotNull(actual);
        assertEquals(expected.name(), actual.name());
        assertEquals(expected.description(), actual.description());
        assertNull(actual.teacher());
    }

    @Test
    public void shouldFindListOfSubjects() {
        // given
        SubjectRequestApiDto firstSubject = subjectUtil.prepareSubjectRequestDto(null);
        SubjectRequestApiDto secondSubject = subjectUtil.prepareSubjectRequestDto("Chemia", null);

        SubjectId firstSubjectId = subjectService.createNewSubject(firstSubject).subjectId();
        assertNotNull(firstSubjectId);
        SubjectId secondSubjectId = subjectService.createNewSubject(secondSubject).subjectId();
        assertNotNull(secondSubjectId);

        // when
        PageDto<SubjectResponseApiDto> actual = subjectService.findAllSubjects(PageRequest.of(0, 20));

        // then
        assertEquals(2, actual.getContent().size());

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
        String expectedExceptionMessage = resourceCreator.of(SubjectValidators.EXCEPTION_MESSAGE_SUBJECT_ALREADY_EXIST, dto.name(), find(SchoolClass.class, dto.schoolClassId()).getClassName());
        assertEquals(expectedExceptionMessage, exception.getErrors().get(0).getCause());


    }
}
