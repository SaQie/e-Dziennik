package pl.edziennik.eDziennik.schoolclass;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import pl.edziennik.eDziennik.BaseTest;
import pl.edziennik.eDziennik.server.exceptions.BusinessException;
import pl.edziennik.eDziennik.server.exceptions.EntityNotFoundException;
import pl.edziennik.eDziennik.domain.school.domain.School;
import pl.edziennik.eDziennik.domain.schoolclass.domain.SchoolClass;
import pl.edziennik.eDziennik.domain.schoolclass.dto.SchoolClassRequestApiDto;
import pl.edziennik.eDziennik.domain.schoolclass.dto.SchoolClassResponseApiDto;
import pl.edziennik.eDziennik.domain.schoolclass.services.SchoolClassService;
import pl.edziennik.eDziennik.domain.schoolclass.services.validator.SchoolClassValidators;
import pl.edziennik.eDziennik.domain.teacher.domain.Teacher;
import pl.edziennik.eDziennik.domain.teacher.dto.TeacherRequestApiDto;
import pl.edziennik.eDziennik.domain.teacher.dto.TeacherResponseApiDto;
import pl.edziennik.eDziennik.domain.teacher.services.TeacherService;
import pl.edziennik.eDziennik.teacher.TeacherIntegrationTestUtil;


import static org.assertj.core.api.Assertions.assertThat;
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
    public void prepareDb() {
        clearDb();
        fillDbWithData();
    }

    @Test
    public void shouldInsertNewSchoolClass() {
        // given
        SchoolClassRequestApiDto expected = util.prepareSchoolClassRequest();

        // when
        Long id = service.createSchoolClass(expected).getId();

        // then
        assertNotNull(id);
        SchoolClass actual = find(SchoolClass.class, id);
        assertEquals(expected.getIdSchool(), actual.getSchool().getId());
        assertEquals(expected.getIdClassTeacher(), actual.getTeacher());
        assertEquals(expected.getClassName(), actual.getClassName());
    }

    @Test
    public void shouldUpdateSchoolClass() {
        // given
        TeacherRequestApiDto teacherRequest = teacherUtil.prepareTeacherRequestDto();
        Long teacherId = teacherService.register(teacherRequest).getId();
        SchoolClassRequestApiDto dto = util.prepareSchoolClassRequest();
        Long id = service.createSchoolClass(dto).getId();
        SchoolClassRequestApiDto expected = util.prepareSchoolClassRequest("5B", teacherId);

        // when
        Long updated = service.updateSchoolClass(id, expected).getId();

        // then
        assertNotNull(updated);
        assertEquals(id, updated);

        SchoolClass actual = find(SchoolClass.class, updated);
        assertEquals(expected.getIdSchool(), actual.getSchool().getId());
        assertEquals(expected.getIdClassTeacher(), actual.getTeacher().getId());
        assertEquals(expected.getClassName(), actual.getClassName());

    }

    @Test
    public void shouldDeteleSchoolClass() {
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
    public void shouldFindSchoolClassById() {
        // given
        SchoolClassRequestApiDto expected = util.prepareSchoolClassRequest();
        Long id = service.createSchoolClass(expected).getId();
        assertNotNull(id);

        // when
        SchoolClassResponseApiDto actual = service.findSchoolClassById(id);

        // then
        assertNotNull(actual);
        assertEquals(expected.getIdSchool(), actual.getSchool().getId());
        assertNull(actual.getSupervisingTeacher());
        assertEquals(expected.getClassName(), actual.getClassName());
    }

    @Test
    public void shouldThrowsExceptionWhenTryingToSaveSchoolClassIfTeacherNotExist() {
        // given
        Long idTeacher = 222L;
        SchoolClassRequestApiDto expected = util.prepareSchoolClassRequest(idTeacher);

        // when

        Exception exception = assertThrows(EntityNotFoundException.class, () -> service.createSchoolClass(expected));

        // then
        assertEquals(exception.getMessage(), resourceCreator.of("not.found.message", idTeacher, Teacher.class.getSimpleName()));

    }

    @Test
    public void shouldThrowsExceptionWhenTryingToSaveSchoolClassIfSchoolNotExist() {
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
        assertEquals(exception.getMessage(), resourceCreator.of("not.found.message", idSchool, School.class.getSimpleName()));
    }

    @Test
    public void shouldThrowsBusinessExceptionWhenTryingToSaveNewSchoolClassAndSchoolClassAlreadyExist() {
        // given
        SchoolClassRequestApiDto requestApiDto = util.prepareSchoolClassRequest();
        Long id = service.createSchoolClass(requestApiDto).getId();
        assertNotNull(id);

        // when
        SchoolClassRequestApiDto requestApiDto2 = util.prepareSchoolClassRequest();
        BusinessException exception = assertThrows(BusinessException.class, () -> service.createSchoolClass(requestApiDto2));

        // then
        assertEquals(1, exception.getErrors().size());
        assertEquals(SchoolClassValidators.SCHOOL_CLASS_ALREADY_EXISTS_VALIDATOR_NAME, exception.getErrors().get(0).getErrorThrownedBy());
        assertEquals(SchoolClassRequestApiDto.CLASS_NAME, exception.getErrors().get(0).getField());
        String expectedExceptionMessage = resourceCreator.of(SchoolClassValidators.EXCEPTION_MESSAGE_SCHOOL_CLASS_ALREADY_EXIST, requestApiDto.getClassName(), find(School.class, requestApiDto.getIdSchool()).getName());
        assertEquals(expectedExceptionMessage, exception.getErrors().get(0).getCause());
    }

    @Test
    public void shouldThrowsBusinessExceptionWhenTryingToSaveNewSchoolClassAndTeacherIsAlreadySupervisingTeacher() {
        // given
        // create teacher
        TeacherRequestApiDto teacherDto = teacherUtil.prepareTeacherRequestDto();
        TeacherResponseApiDto teacherResponseDto = teacherService.register(teacherDto);

        // create school class with teacher
        SchoolClassRequestApiDto requestApiDto = util.prepareSchoolClassRequest(teacherResponseDto.getId());
        Long id = service.createSchoolClass(requestApiDto).getId();
        assertNotNull(id);

        // create second school class with the same teacher
        SchoolClassRequestApiDto schoolClassRequestApiDto = util.prepareSchoolClassRequest("9B", teacherResponseDto.getId());

        // when
        BusinessException exception = assertThrows(BusinessException.class, () -> service.createSchoolClass(schoolClassRequestApiDto));

        // then
        assertEquals(1, exception.getErrors().size());
        assertEquals(SchoolClassValidators.TEACHER_IS_ALREADY_SUPERVISING_TEACHER_VALIDATOR_NAME, exception.getErrors().get(0).getErrorThrownedBy());
        assertEquals(SchoolClassRequestApiDto.ID_CLASS_TEACHER, exception.getErrors().get(0).getField());
        String expectedExceptionMessage = resourceCreator.of(SchoolClassValidators.EXCEPTION_MESSAGE_TEACHER_IS_ALREADY_SUPERVISING_TEACHER, teacherResponseDto.getFirstName() + " " + teacherResponseDto.getLastName(), requestApiDto.getClassName());
        assertEquals(expectedExceptionMessage, exception.getErrors().get(0).getCause());
    }

    @Test
    public void shouldThrowsBusinessExceptionWhenTryingToSaveNewSchoolClassAndTeacherNotBelongToSchool() {
        // given
        // create teacher
        TeacherRequestApiDto teacherDto = teacherUtil.prepareTeacherRequestDto();
        TeacherResponseApiDto teacherResponseDto = teacherService.register(teacherDto);

        SchoolClassRequestApiDto requestApiDto = util.prepareSchoolClassRequest(teacherResponseDto.getId(), 101L);

        // when
        BusinessException exception = assertThrows(BusinessException.class, () -> service.createSchoolClass(requestApiDto));

        // then
        assertEquals(1, exception.getErrors().size());
        assertEquals(SchoolClassValidators.TEACHER_NOT_BELONGS_TO_SCHOOL_VALIDATOR_NAME, exception.getErrors().get(0).getErrorThrownedBy());
        assertEquals(SchoolClassRequestApiDto.ID_CLASS_TEACHER, exception.getErrors().get(0).getField());
        String expectedExceptionMessage = resourceCreator.of(SchoolClassValidators.EXCEPTION_MESSAGE_TEACHER_NOT_BELONG_TO_SCHOOL, teacherResponseDto.getFirstName() + " " + teacherResponseDto.getLastName(), find(School.class, 101L).getName());
        assertEquals(expectedExceptionMessage, exception.getErrors().get(0).getCause());
    }
}
