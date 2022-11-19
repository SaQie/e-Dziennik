package pl.edziennik.eDziennik.schoolclass;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import pl.edziennik.eDziennik.BaseTest;
import pl.edziennik.eDziennik.exceptions.EntityNotFoundException;
import pl.edziennik.eDziennik.server.basics.BaseDao;
import pl.edziennik.eDziennik.server.school.domain.School;
import pl.edziennik.eDziennik.server.schoolclass.domain.SchoolClass;
import pl.edziennik.eDziennik.server.schoolclass.domain.dto.SchoolClassRequestApiDto;
import pl.edziennik.eDziennik.server.schoolclass.domain.dto.SchoolClassResponseApiDto;
import pl.edziennik.eDziennik.server.schoolclass.services.SchoolClassService;
import pl.edziennik.eDziennik.server.schoollevel.domain.SchoolLevel;
import pl.edziennik.eDziennik.server.teacher.domain.Teacher;
import pl.edziennik.eDziennik.server.teacher.domain.dto.TeacherRequestApiDto;
import pl.edziennik.eDziennik.server.teacher.services.TeacherService;
import pl.edziennik.eDziennik.teacher.TeacherIntegrationTestUtil;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@Rollback
public class SchoolClassIntegrationTest extends BaseTest {

    @Autowired
    private SchoolClassService service;

    @Autowired
    private TeacherService teacherService;

    private final SchoolClassIntergrationTestUtil util;
    private final TeacherIntegrationTestUtil teacherUtil;

    public SchoolClassIntegrationTest() {
        this.util = new SchoolClassIntergrationTestUtil();
        this.teacherUtil = new TeacherIntegrationTestUtil();
    }

    @BeforeEach
    public void prepareDb(){
        clearDb();
        fillDbWithData();
    }

    @Test
    public void shouldInsertNewSchoolClass(){
        // given
        SchoolClassRequestApiDto expected = util.prepareSchoolClassRequest();

        // when
        Long id = service.createSchoolClass(expected).getId();

        // then
        assertNotNull(id);
        SchoolClass actual = find(SchoolClass.class, id);
        assertEquals(expected.getSchool(), actual.getSchool().getId());
        assertEquals(expected.getSupervisingTeacherId(), actual.getTeacher());
        assertEquals(expected.getClassName(), actual.getClassName());
    }

    @Test
    public void shouldUpdateSchoolClass(){
        // given
        TeacherRequestApiDto teacherRequest = teacherUtil.prepareTeacherRequestDto();
        Long teacherId = teacherService.register(teacherRequest).getId();
        SchoolClassRequestApiDto dto = util.prepareSchoolClassRequest();
        Long id = service.createSchoolClass(dto).getId();
        SchoolClassRequestApiDto expected = util.prepareSchoolClassRequest("5B",teacherId);

        // when
        Long updated = service.updateSchoolClass(id, expected).getId();

        // then
        assertNotNull(updated);
        assertEquals(id,updated);

        SchoolClass actual = find(SchoolClass.class, updated);
        assertEquals(expected.getSchool(), actual.getSchool().getId());
        assertEquals(expected.getSupervisingTeacherId(), actual.getTeacher().getId());
        assertEquals(expected.getClassName(), actual.getClassName());

    }

    @Test
    public void shouldDeteleSchoolClass(){
        // given
        SchoolClassRequestApiDto expected = util.prepareSchoolClassRequest();
        Long id = service.createSchoolClass(expected).getId();
        assertNotNull(id);

        // when
        service.deleteSchoolClassById(id);

        // then
        SchoolClass schoolClass = find(SchoolClass.class, id);
        assertNull(schoolClass);
    }

    @Test
    public void shouldFindSchoolClassById(){
        // given
        SchoolClassRequestApiDto expected = util.prepareSchoolClassRequest();
        Long id = service.createSchoolClass(expected).getId();
        assertNotNull(id);

        // when
        SchoolClassResponseApiDto actual = service.findSchoolClassById(id);

        // then
        assertNotNull(actual);
        assertEquals(expected.getSchool(), actual.getIdSchool());
        assertEquals(expected.getSupervisingTeacherId(), actual.getIdSupervisingTeacher());
        assertEquals(expected.getClassName(), actual.getClassName());
    }

    @Test
    public void shouldThrowsExceptionWhenTryingToSaveSchoolClassIfTeacherNotExist(){
        // given
        Long idTeacher = 222L;
        SchoolClassRequestApiDto expected = util.prepareSchoolClassRequest(idTeacher);

        // when

        Exception exception = assertThrows(EntityNotFoundException.class, () -> service.createSchoolClass(expected));

        // then
        assertEquals(exception.getMessage(), BaseDao.BaseDaoExceptionMessage.createNotFoundExceptionMessage(Teacher.class.getSimpleName(), idTeacher));

    }

    @Test
    public void shouldThrowsExceptionWhenTryingToSaveSchoolClassIfSchoolNotExist(){
        // given
        Long idTeacher = 1L;
        TeacherRequestApiDto teacherRequest = teacherUtil.prepareTeacherRequestDto();
        Long teacherId = teacherService.register(teacherRequest).getId();
        assertNotNull(teacherId);

        Long idSchool = 222L;
        SchoolClassRequestApiDto expected = util.prepareSchoolClassRequest(idTeacher, idSchool);

        // when
        Exception exception = assertThrows(EntityNotFoundException.class, () -> service.createSchoolClass(expected));

        // then
        assertEquals(exception.getMessage(), BaseDao.BaseDaoExceptionMessage.createNotFoundExceptionMessage(School.class.getSimpleName(), idSchool));
    }


}
