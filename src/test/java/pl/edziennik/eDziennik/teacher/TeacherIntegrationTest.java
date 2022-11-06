package pl.edziennik.eDziennik.teacher;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import pl.edziennik.eDziennik.BaseTest;
import pl.edziennik.eDziennik.server.teacher.domain.Teacher;
import pl.edziennik.eDziennik.server.teacher.domain.dto.TeacherRequestApiDto;
import pl.edziennik.eDziennik.server.teacher.domain.dto.TeacherResponseApiDto;
import pl.edziennik.eDziennik.server.teacher.services.TeacherService;

import javax.persistence.EntityNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
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
        assertEquals(expected.getAdress(), actual.getAdress());
        assertEquals(expected.getPesel(), actual.getPESEL());
        assertEquals(expected.getCity(), actual.getCity());
        assertEquals(expected.getUsername(), actual.getUsername());
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
        assertThrows(EntityNotFoundException.class, () -> service.findTeacherById(id));
    }

    @Test
    public void shouldFindListOfStudents(){
        // given
        TeacherRequestApiDto firstTeacher = util.prepareTeacherRequestDto();
        TeacherRequestApiDto secondTeacher = util.prepareTeacherRequestDto("TEST", "TESTOWY", "12356");
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
        assertEquals(expected.getAdress(), actual.getAdress());
        assertEquals(expected.getPesel(), actual.getPesel());
        assertEquals(expected.getCity(), actual.getCity());
        assertEquals(ROLE_TEACHER, actual.getIdRole());
    }

    @Test
    public void shouldThrowsExceptionWhenRoleNotExist(){
        // given
        String role = "TEST";
        TeacherRequestApiDto dto = util.prepareTeacherRequestDto(role);

        // when
        Throwable throwable = catchThrowable(() -> service.register(dto));

        // then
        assertThat(throwable).hasMessage("Role with name " + role + " not exist");
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
        Throwable throwable = catchThrowable(() -> service.register(dto));

        // then
        assertThat(throwable).hasMessageContaining("School with id " + idSchool + " not found");
    }


}
