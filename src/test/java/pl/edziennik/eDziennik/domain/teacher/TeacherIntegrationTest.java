package pl.edziennik.eDziennik.domain.teacher;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import pl.edziennik.eDziennik.BaseTesting;
import pl.edziennik.eDziennik.domain.school.domain.School;
import pl.edziennik.eDziennik.domain.teacher.domain.Teacher;
import pl.edziennik.eDziennik.domain.teacher.dto.TeacherRequestApiDto;
import pl.edziennik.eDziennik.domain.teacher.dto.TeacherResponseApiDto;
import pl.edziennik.eDziennik.domain.teacher.services.validator.TeacherValidators;
import pl.edziennik.eDziennik.server.exceptions.BusinessException;
import pl.edziennik.eDziennik.server.exceptions.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@Rollback
public class TeacherIntegrationTest extends BaseTesting {

    @Test
    public void shouldSaveNewTeacher() {
        // given
        TeacherRequestApiDto expected = teacherUtil.prepareTeacherRequestDto();

        // when
        Long id = teacherService.register(expected).id();

        // then
        assertNotNull(id);
        Teacher actual = find(Teacher.class, id);

        assertEquals(expected.firstName(), actual.getPersonInformation().getFirstName());
        assertEquals(expected.lastName(), actual.getPersonInformation().getLastName());
        assertEquals(expected.address(), actual.getAddress().getAddress());
        assertEquals(expected.pesel(), actual.getPersonInformation().getPesel());
        assertEquals(expected.city(), actual.getAddress().getCity());
        assertEquals(expected.username(), actual.getUser().getUsername());
        assertEquals(teacherUtil.defaultRole, actual.getUser().getRole().getName());
    }

    @Test
    public void shouldUpdateTeacher() {
        // given
        TeacherRequestApiDto dto = teacherUtil.prepareTeacherRequestDto();
        Long id = teacherService.register(dto).id();
        TeacherRequestApiDto expected = teacherUtil.prepareTeacherRequestDto("AfterEdit", "AfterEdit2", "55555251",
                "test2" + "@example.com");

        // when
        Long updated = teacherService.updateTeacher(id, expected).id();

        // then
        assertNotNull(updated);
        assertEquals(updated, id);
        Teacher actual = find(Teacher.class, updated);

        assertEquals(expected.firstName(), actual.getPersonInformation().getFirstName());
        assertEquals(expected.lastName(), actual.getPersonInformation().getLastName());
        assertEquals(expected.address(), actual.getAddress().getAddress());
        assertEquals(expected.pesel(), actual.getPersonInformation().getPesel());
        assertEquals(expected.city(), actual.getAddress().getCity());
        assertEquals(teacherUtil.defaultRole, actual.getUser().getRole().getName());

    }

    @Test
    public void shouldThrowExceptionWhenUpdateAndSchoolNotExists(){
        // given
        TeacherRequestApiDto dto = teacherUtil.prepareTeacherRequestDto();
        Long id = teacherService.register(dto).id();
        TeacherRequestApiDto expected = teacherUtil.prepareTeacherRequestDto(999999L);

        // when

        // then
        Exception exception = assertThrows(EntityNotFoundException.class, () -> teacherService.updateTeacher(id, expected));
        assertEquals(exception.getMessage(), resourceCreator.of("not.found.message", expected.idSchool(), School.class.getSimpleName()));
    }

    @Test
    public void shouldDeleteTeacher() {
        // given
        TeacherRequestApiDto dto = teacherUtil.prepareTeacherRequestDto();
        Long id = teacherService.register(dto).id();
        assertNotNull(id);

        // when
        teacherService.deleteTeacherById(id);

        // then
        Exception exception = assertThrows(EntityNotFoundException.class, () -> teacherService.findTeacherById(id));
        assertEquals(exception.getMessage(), resourceCreator.of("not.found.message", id,
                Teacher.class.getSimpleName()));
    }

    @Test
    public void shouldFindListOfTeachers() {
        // given
        TeacherRequestApiDto firstTeacher = teacherUtil.prepareTeacherRequestDto();
        TeacherRequestApiDto secondTeacher = teacherUtil.prepareTeacherRequestDto("TEST1", "TESTOWY2", "12356", "test2" +
                "@example.com");
        Long firstTeacherId = teacherService.register(firstTeacher).id();
        Long secondTeacherId = teacherService.register(secondTeacher).id();
        assertNotNull(firstTeacherId);
        assertNotNull(secondTeacherId);

        // when
        int actual = teacherService.findAllTeachers(PageRequest.of(0, 20)).getContent().size();

        // then
        assertEquals(2, actual);

    }


    @Test
    public void shouldFindTeacherWithGivenId() {
        // given
        TeacherRequestApiDto expected = teacherUtil.prepareTeacherRequestDto();
        Long id = teacherService.register(expected).id();
        assertNotNull(id);

        // when
        TeacherResponseApiDto actual = teacherService.findTeacherById(id);

        // then
        assertNotNull(actual);
        assertEquals(expected.firstName(), actual.firstName());
        assertEquals(expected.lastName(), actual.lastName());
        assertEquals(expected.address(), actual.address());
        assertEquals(expected.pesel(), actual.pesel());
        assertEquals(expected.city(), actual.city());
        assertEquals(ROLE_TEACHER_TEXT, actual.role());
    }

    @Test
    public void shouldAssignDefaultRoleIfRoleIsEmpty() {
        // given
        TeacherRequestApiDto dto = teacherUtil.prepareTeacherRequestDto();
        assertNull(dto.role());

        // when
        String actualRole = teacherService.register(dto).role();

        // then
        assertEquals(ROLE_TEACHER_TEXT, actualRole);

    }

    @Test
    public void shouldThrowsExceptionWhenSchoolNotExist() {
        // given
        Long idSchool = 99L;
        TeacherRequestApiDto dto = teacherUtil.prepareTeacherRequestDto(idSchool);

        // when
        Exception exception = assertThrows(EntityNotFoundException.class, () -> teacherService.register(dto));

        // then
        assertEquals(exception.getMessage(), resourceCreator.of("not.found.message", idSchool,
                School.class.getSimpleName()));
    }

    @Test
    public void shouldThrowsBusinessExceptionWhenTryingToRegisterAndPeselAlreadyExist() {
        // given
        TeacherRequestApiDto dto = teacherUtil.prepareTeacherRequestDto("asdasd", "00000000000", "test2@example.com");
        teacherService.register(dto);
        TeacherRequestApiDto dto2 = teacherUtil.prepareTeacherRequestDto("asdasd2", "00000000000", "test3@example.com");

        // when
        BusinessException exception = assertThrows(BusinessException.class, () -> teacherService.register(dto2));

        // then
        assertEquals(1, exception.getErrors().size());
        Assertions.assertEquals(TeacherValidators.TEACHER_PESEL_NOT_UNIQUE_VALIDATOR_NAME,
                exception.getErrors().get(0).getErrorThrownedBy());
        assertEquals(TeacherRequestApiDto.PESEL, exception.getErrors().get(0).getField());
        String expectedExceptionMessage = resourceCreator.of(TeacherValidators.EXCEPTION_MESSAGE_PESEL_NOT_UNIQUE,
                dto.pesel());
        assertEquals(expectedExceptionMessage, exception.getErrors().get(0).getCause());
    }


}
