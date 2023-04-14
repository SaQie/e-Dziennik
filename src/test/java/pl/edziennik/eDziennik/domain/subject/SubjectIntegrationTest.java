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
import pl.edziennik.eDziennik.domain.teacher.dto.TeacherRequestApiDto;
import pl.edziennik.eDziennik.server.basics.page.PageDto;
import pl.edziennik.eDziennik.server.exceptions.BusinessException;
import pl.edziennik.eDziennik.server.exceptions.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@Rollback
public class SubjectIntegrationTest extends BaseTesting {

    @Test
    public void shouldSaveNewSubjectWithTeacher() {
        // given
        TeacherRequestApiDto dto = teacherUtil.prepareTeacherRequestDto();
        Long teacherId = teacherService.register(dto).id();
        assertNotNull(teacherId);

        SubjectRequestApiDto expected = subjectUtil.prepareSubjectRequestDto(teacherId);

        // when
        Long subjectId = subjectService.createNewSubject(expected).id();

        // then
        assertNotNull(subjectId);
        Subject actual = find(Subject.class, subjectId);
        assertNotNull(actual);

        assertEquals(expected.name(), actual.getName());
        assertEquals(expected.description(), actual.getDescription());
        assertEquals(expected.idTeacher(), actual.getTeacher().getTeacherId().value());
    }

    @Test
    public void shouldUpdateSubject() {
        // given
        TeacherRequestApiDto teacherDto = teacherUtil.prepareTeacherRequestDto();
        Long teacherId = teacherService.register(teacherDto).id();
        assertNotNull(teacherId);

        SubjectRequestApiDto dto = subjectUtil.prepareSubjectRequestDto(teacherId);
        Long subjectId = subjectService.createNewSubject(dto).id();
        SubjectRequestApiDto expected = subjectUtil.prepareSubjectRequestDto("Chemia", teacherId);

        // when
        Long updated = subjectService.updateSubject(SubjectId.wrap(subjectId), expected).id();

        // then
        assertNotNull(updated);
        assertEquals(updated, subjectId);
        Subject actual = find(Subject.class, updated);
        assertNotNull(actual);

        assertEquals(expected.name(), actual.getName());
        assertEquals(expected.description(), actual.getDescription());
        assertEquals(expected.idTeacher(), actual.getTeacher().getTeacherId().value());
    }

    @Test
    public void shouldSaveNewSubjectWithoutTeacher() {
        // given
        SubjectRequestApiDto expected = subjectUtil.prepareSubjectRequestDto(null);

        // when
        Long subjectId = subjectService.createNewSubject(expected).id();

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
        Long idSubject = subjectService.createNewSubject(expected).id();
        assertNotNull(idSubject);

        // when
        SubjectResponseApiDto actual = subjectService.findSubjectById(SubjectId.wrap(idSubject));

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

        Long firstSubjectId = subjectService.createNewSubject(firstSubject).id();
        assertNotNull(firstSubjectId);
        Long secondSubjectId = subjectService.createNewSubject(secondSubject).id();
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
        String expectedExceptionMessage = resourceCreator.of(SubjectValidators.EXCEPTION_MESSAGE_SUBJECT_ALREADY_EXIST, dto.name(), find(SchoolClass.class, dto.idSchoolClass()).getClassName());
        assertEquals(expectedExceptionMessage, exception.getErrors().get(0).getCause());


    }
}
