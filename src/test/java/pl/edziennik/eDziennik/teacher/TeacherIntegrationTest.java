package pl.edziennik.eDziennik.teacher;

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
import pl.edziennik.eDziennik.server.teacher.domain.Teacher;
import pl.edziennik.eDziennik.server.teacher.domain.dto.TeacherRequestApiDto;
import pl.edziennik.eDziennik.server.teacher.domain.dto.TeacherResponseApiDto;
import pl.edziennik.eDziennik.server.teacher.services.TeacherService;
import pl.edziennik.eDziennik.server.teacher.services.validator.TeacherValidators;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@Rollback
public class TeacherIntegrationTest extends BaseTest {

    private final TeacherIntegrationTestUtil util;

    public TeacherIntegrationTest() {
        this.util = new TeacherIntegrationTestUtil();
    }

    @Autowired
    private TeacherService service;


    @BeforeEach
    public void prepareDb(){
        clearDb();
        fillDbWithData();
    }


    @Test
    public void shouldSaveNewTeacher(){
        // given
        TeacherRequestApiDto expected = util.prepareTeacherRequestDto();

        // when
        Long id = service.register(expected).getId();

        // then
        assertNotNull(id);
        Teacher actual = find(Teacher.class, id);

        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getAddress(), actual.getAddress().getAddress());
        assertEquals(expected.getPesel(), actual.getPESEL());
        assertEquals(expected.getCity(), actual.getAddress().getCity());
        assertEquals(expected.getUsername(), actual.getUsername());
        assertEquals(util.defaultRole, actual.getRole().getName());
    }

    @Test
    public void shouldUpdateTeacher(){
        // given
        TeacherRequestApiDto dto = util.prepareTeacherRequestDto();
        Long id = service.register(dto).getId();
        TeacherRequestApiDto expected = util.prepareTeacherRequestDto("AfterEdit", "AfterEdit2", "5555551");

        // when
        Long updated = service.updateTeacher(id, expected).getId();

        // then
        assertNotNull(updated);
        assertEquals(updated,id);
        Teacher actual = find(Teacher.class, updated);

        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getAddress(), actual.getAddress().getAddress());
        assertEquals(expected.getPesel(), actual.getPESEL());
        assertEquals(expected.getCity(), actual.getAddress().getCity());
        assertEquals(util.defaultRole, actual.getRole().getName());

    }

    @Test
    public void shouldDeleteTeacher(){
        // given
        TeacherRequestApiDto dto = util.prepareTeacherRequestDto();
        Long id = service.register(dto).getId();
        assertNotNull(id);

        // when
        service.deleteTeacherById(id);

        // then
        Exception exception = assertThrows(EntityNotFoundException.class, () -> service.findTeacherById(id));
        assertEquals(exception.getMessage(), BaseDao.BaseDaoExceptionMessage.createNotFoundExceptionMessage(Teacher.class.getSimpleName(), id));
    }

    @Test
    public void shouldFindListOfTeachers(){
        // given
        TeacherRequestApiDto firstTeacher = util.prepareTeacherRequestDto();
        TeacherRequestApiDto secondTeacher = util.prepareTeacherRequestDto("TEST1", "TESTOWY2", "12356");
        Long firstTeacherId = service.register(firstTeacher).getId();
        Long secondTeacherId = service.register(secondTeacher).getId();
        assertNotNull(firstTeacherId);
        assertNotNull(secondTeacherId);

        // when
        int actual = service.findAllTeachers().size();

        // then
        assertEquals(2,actual);

    }


    @Test
    public void shouldFindTeacherWithGivenId(){
        // given
        TeacherRequestApiDto expected = util.prepareTeacherRequestDto();
        Long id = service.register(expected).getId();
        assertNotNull(id);

        // when
        TeacherResponseApiDto actual = service.findTeacherById(id);

        // then
        assertNotNull(actual);
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getAddress(), actual.getAdress());
        assertEquals(expected.getPesel(), actual.getPesel());
        assertEquals(expected.getCity(), actual.getCity());
        assertEquals(ROLE_TEACHER, actual.getIdRole());
    }

    @Test
    public void shouldAssignDefaultRoleIfRoleIsEmpty(){
        // given
        TeacherRequestApiDto dto = util.prepareTeacherRequestDto();
        assertNull(dto.getRole());

        // when
        Long idActualRole = service.register(dto).getIdRole();

        // then
        assertEquals(ROLE_TEACHER, idActualRole);

    }
    @Test
    public void shouldThrowsExceptionWhenSchoolNotExist(){
        // given
        Long idSchool = 99L;
        TeacherRequestApiDto dto = util.prepareTeacherRequestDto(idSchool);

        // when
        Exception exception = assertThrows(EntityNotFoundException.class, () -> service.register(dto));

        // then
        assertEquals(exception.getMessage(), BaseDao.BaseDaoExceptionMessage.createNotFoundExceptionMessage(School.class.getSimpleName(), idSchool));
    }

    @Test
    public void shouldThrowsBusinessExceptionWhenTryingToRegisterAndTeacherAlreadyExist(){
        // given
        String expectedValidatorName = "TeacherAlreadyExistValidator";
        TeacherRequestApiDto dto = util.prepareTeacherRequestDto();
        service.register(dto);

        // when
        BusinessException exception = assertThrows(BusinessException.class, () -> service.register(dto));

        // then
        assertEquals(1, exception.getErrors().size());
        assertEquals(expectedValidatorName, exception.getErrors().get(0).getErrorThrownedBy());
        assertEquals(TeacherRequestApiDto.USERNAME, exception.getErrors().get(0).getField());
        String expectedExceptionMessage = resourceCreator.of(TeacherValidators.EXCEPTION_MESSAGE_TEACHER_ALREADY_EXIST, Teacher.class.getSimpleName(), dto.getUsername());
        assertEquals(expectedExceptionMessage, exception.getErrors().get(0).getCause());
    }


}
