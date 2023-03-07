package pl.edziennik.eDziennik.domain.teacher;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import pl.edziennik.eDziennik.BaseTest;
import pl.edziennik.eDziennik.domain.teacher.services.validator.TeacherValidators;
import pl.edziennik.eDziennik.server.basics.dto.PageRequest;
import pl.edziennik.eDziennik.server.exceptions.BusinessException;
import pl.edziennik.eDziennik.server.exceptions.EntityNotFoundException;
import pl.edziennik.eDziennik.domain.school.domain.School;
import pl.edziennik.eDziennik.domain.teacher.domain.Teacher;
import pl.edziennik.eDziennik.domain.teacher.dto.TeacherRequestApiDto;
import pl.edziennik.eDziennik.domain.teacher.dto.TeacherResponseApiDto;
import pl.edziennik.eDziennik.domain.teacher.services.TeacherService;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@Rollback
public class TeacherIntegrationTest extends BaseTest {

    @Test
    public void shouldSaveNewTeacher() {
        // given
        TeacherRequestApiDto expected = teacherUtil.prepareTeacherRequestDto();

        // when
        Long id = teacherService.register(expected).getId();

        // then
        assertNotNull(id);
        Teacher actual = find(Teacher.class, id);

        assertEquals(expected.getFirstName(), actual.getPersonInformation().getFirstName());
        assertEquals(expected.getLastName(), actual.getPersonInformation().getLastName());
        assertEquals(expected.getAddress(), actual.getAddress().getAddress());
        assertEquals(expected.getPesel(), actual.getPersonInformation().getPesel());
        assertEquals(expected.getCity(), actual.getAddress().getCity());
        assertEquals(expected.getUsername(), actual.getUser().getUsername());
        assertEquals(teacherUtil.defaultRole, actual.getUser().getRole().getName());
    }

    @Test
    public void shouldUpdateTeacher() {
        // given
        TeacherRequestApiDto dto = teacherUtil.prepareTeacherRequestDto();
        Long id = teacherService.register(dto).getId();
        TeacherRequestApiDto expected = teacherUtil.prepareTeacherRequestDto("AfterEdit", "AfterEdit2", "5555551",
                "test2" + "@example.com");

        // when
        Long updated = teacherService.updateTeacher(id, expected).getId();

        // then
        assertNotNull(updated);
        assertEquals(updated, id);
        Teacher actual = find(Teacher.class, updated);

        assertEquals(expected.getFirstName(), actual.getPersonInformation().getFirstName());
        assertEquals(expected.getLastName(), actual.getPersonInformation().getLastName());
        assertEquals(expected.getAddress(), actual.getAddress().getAddress());
        assertEquals(expected.getPesel(), actual.getPersonInformation().getPesel());
        assertEquals(expected.getCity(), actual.getAddress().getCity());
        assertEquals(teacherUtil.defaultRole, actual.getUser().getRole().getName());

    }

    @Test
    public void shouldDeleteTeacher() {
        // given
        TeacherRequestApiDto dto = teacherUtil.prepareTeacherRequestDto();
        Long id = teacherService.register(dto).getId();
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
        Long firstTeacherId = teacherService.register(firstTeacher).getId();
        Long secondTeacherId = teacherService.register(secondTeacher).getId();
        assertNotNull(firstTeacherId);
        assertNotNull(secondTeacherId);

        // when
        int actual = teacherService.findAllTeachers(new PageRequest(1, 20)).getEntities().size();

        // then
        assertEquals(2, actual);

    }


    @Test
    public void shouldFindTeacherWithGivenId() {
        // given
        TeacherRequestApiDto expected = teacherUtil.prepareTeacherRequestDto();
        Long id = teacherService.register(expected).getId();
        assertNotNull(id);

        // when
        TeacherResponseApiDto actual = teacherService.findTeacherById(id);

        // then
        assertNotNull(actual);
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getAddress(), actual.getAddress());
        assertEquals(expected.getPesel(), actual.getPesel());
        assertEquals(expected.getCity(), actual.getCity());
        assertEquals(ROLE_TEACHER_TEXT, actual.getRole());
    }

    @Test
    public void shouldAssignDefaultRoleIfRoleIsEmpty() {
        // given
        TeacherRequestApiDto dto = teacherUtil.prepareTeacherRequestDto();
        assertNull(dto.getRole());

        // when
        String actualRole = teacherService.register(dto).getRole();

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
                dto.getPesel());
        assertEquals(expectedExceptionMessage, exception.getErrors().get(0).getCause());
    }


}
