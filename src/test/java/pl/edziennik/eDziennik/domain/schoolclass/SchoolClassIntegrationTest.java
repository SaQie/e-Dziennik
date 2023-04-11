package pl.edziennik.eDziennik.domain.schoolclass;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import pl.edziennik.eDziennik.BaseTesting;
import pl.edziennik.eDziennik.domain.school.domain.School;
import pl.edziennik.eDziennik.domain.schoolclass.domain.SchoolClass;
import pl.edziennik.eDziennik.domain.schoolclass.domain.wrapper.SchoolClassId;
import pl.edziennik.eDziennik.domain.schoolclass.dto.SchoolClassRequestApiDto;
import pl.edziennik.eDziennik.domain.schoolclass.dto.SchoolClassResponseApiDto;
import pl.edziennik.eDziennik.domain.schoolclass.services.validator.SchoolClassValidators;
import pl.edziennik.eDziennik.domain.teacher.domain.Teacher;
import pl.edziennik.eDziennik.domain.teacher.dto.TeacherRequestApiDto;
import pl.edziennik.eDziennik.domain.teacher.dto.TeacherResponseApiDto;
import pl.edziennik.eDziennik.server.exceptions.BusinessException;
import pl.edziennik.eDziennik.server.exceptions.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@Rollback
public class SchoolClassIntegrationTest extends BaseTesting {

    @Test
    public void shouldInsertNewSchoolClass() {
        // given
        SchoolClassRequestApiDto expected = schoolClassUtil.prepareSchoolClassRequest();

        // when
        Long id = schoolClassService.createSchoolClass(expected).id();

        // then
        assertNotNull(id);
        SchoolClass actual = find(SchoolClass.class, id);
        assertEquals(expected.idSchool(), actual.getSchool().getSchoolId().id());
        assertNull(actual.getTeacher());
        assertEquals(expected.className(), actual.getClassName());
    }

    @Test
    public void shouldUpdateSchoolClass() {
        // given
        TeacherRequestApiDto teacherRequest = teacherUtil.prepareTeacherRequestDto();
        Long teacherId = teacherService.register(teacherRequest).id();
        SchoolClassRequestApiDto dto = schoolClassUtil.prepareSchoolClassRequest();
        Long id = schoolClassService.createSchoolClass(dto).id();
        SchoolClassRequestApiDto expected = schoolClassUtil.prepareSchoolClassRequest("5B", teacherId);

        // when
        Long updated = schoolClassService.updateSchoolClass(SchoolClassId.wrap(id), expected).id();

        // then
        assertNotNull(updated);
        assertEquals(id, updated);

        SchoolClass actual = find(SchoolClass.class, updated);
        assertEquals(expected.idSchool(), actual.getSchool().getSchoolId().id());
        assertEquals(expected.idClassTeacher(), actual.getTeacher().getTeacherId().id());
        assertEquals(expected.className(), actual.getClassName());

    }

    @Test
    public void shouldDeteleSchoolClass() {
        // given
        SchoolClassRequestApiDto expected = schoolClassUtil.prepareSchoolClassRequest();
        Long id = schoolClassService.createSchoolClass(expected).id();
        assertNotNull(id);

        // when
        schoolClassService.deleteSchoolClassById(SchoolClassId.wrap(id));

        // then
        SchoolClass schoolClass = find(SchoolClass.class, id);
        assertNull(schoolClass);
    }

    @Test
    public void shouldFindSchoolClassById() {
        // given
        SchoolClassRequestApiDto expected = schoolClassUtil.prepareSchoolClassRequest();
        Long id = schoolClassService.createSchoolClass(expected).id();
        assertNotNull(id);

        // when
        SchoolClassResponseApiDto actual = schoolClassService.findSchoolClassById(SchoolClassId.wrap(id));

        // then
        assertNotNull(actual);
        assertEquals(expected.idSchool(), actual.school().id());
        assertNull(actual.supervisingTeacher());
        assertEquals(expected.className(), actual.className());
    }

    @Test
    public void shouldThrowsExceptionWhenTryingToSaveSchoolClassIfTeacherNotExist() {
        // given
        Long idTeacher = 222L;
        SchoolClassRequestApiDto expected = schoolClassUtil.prepareSchoolClassRequest(idTeacher);

        // when

        Exception exception = assertThrows(EntityNotFoundException.class, () -> schoolClassService.createSchoolClass(expected));

        // then
        assertEquals(exception.getMessage(), resourceCreator.of("not.found.message", idTeacher, Teacher.class.getSimpleName()));

    }

    @Test
    public void shouldThrowsExceptionWhenTryingToSaveSchoolClassIfSchoolNotExist() {
        // given
        Long idTeacher = 1L;
        TeacherRequestApiDto teacherRequest = teacherUtil.prepareTeacherRequestDto();
        Long teacherId = teacherService.register(teacherRequest).id();
        assertNotNull(teacherId);

        Long idSchool = 222L;
        SchoolClassRequestApiDto expected = schoolClassUtil.prepareSchoolClassRequest(idTeacher, idSchool);

        // when
        Exception exception = assertThrows(EntityNotFoundException.class, () -> schoolClassService.createSchoolClass(expected));

        // then
        assertEquals(exception.getMessage(), resourceCreator.of("not.found.message", idSchool, School.class.getSimpleName()));
    }

    @Test
    public void shouldThrowsBusinessExceptionWhenTryingToSaveNewSchoolClassAndSchoolClassAlreadyExist() {
        // given
        SchoolClassRequestApiDto requestApiDto = schoolClassUtil.prepareSchoolClassRequest();
        Long id = schoolClassService.createSchoolClass(requestApiDto).id();
        assertNotNull(id);

        // when
        SchoolClassRequestApiDto requestApiDto2 = schoolClassUtil.prepareSchoolClassRequest();
        BusinessException exception = assertThrows(BusinessException.class, () -> schoolClassService.createSchoolClass(requestApiDto2));

        // then
        assertEquals(1, exception.getErrors().size());
        assertEquals(SchoolClassValidators.SCHOOL_CLASS_ALREADY_EXISTS_VALIDATOR_NAME, exception.getErrors().get(0).getErrorThrownedBy());
        assertEquals(SchoolClassRequestApiDto.CLASS_NAME, exception.getErrors().get(0).getField());
        String expectedExceptionMessage = resourceCreator.of(SchoolClassValidators.EXCEPTION_MESSAGE_SCHOOL_CLASS_ALREADY_EXIST, requestApiDto.className(), find(School.class, requestApiDto.idSchool()).getName());
        assertEquals(expectedExceptionMessage, exception.getErrors().get(0).getCause());
    }

    @Test
    public void shouldThrowsBusinessExceptionWhenTryingToSaveNewSchoolClassAndTeacherIsAlreadySupervisingTeacher() {
        // given
        // create teacher
        TeacherRequestApiDto teacherDto = teacherUtil.prepareTeacherRequestDto();
        TeacherResponseApiDto teacherResponseDto = teacherService.register(teacherDto);

        // create school class with teacher
        SchoolClassRequestApiDto requestApiDto = schoolClassUtil.prepareSchoolClassRequest(teacherResponseDto.id());
        Long id = schoolClassService.createSchoolClass(requestApiDto).id();
        assertNotNull(id);

        // create second school class with the same teacher
        SchoolClassRequestApiDto schoolClassRequestApiDto = schoolClassUtil.prepareSchoolClassRequest("9B", teacherResponseDto.id());

        // when
        BusinessException exception = assertThrows(BusinessException.class, () -> schoolClassService.createSchoolClass(schoolClassRequestApiDto));

        // then
        assertEquals(1, exception.getErrors().size());
        assertEquals(SchoolClassValidators.TEACHER_IS_ALREADY_SUPERVISING_TEACHER_VALIDATOR_NAME, exception.getErrors().get(0).getErrorThrownedBy());
        assertEquals(SchoolClassRequestApiDto.ID_CLASS_TEACHER, exception.getErrors().get(0).getField());
        String expectedExceptionMessage = resourceCreator.of(SchoolClassValidators.EXCEPTION_MESSAGE_TEACHER_IS_ALREADY_SUPERVISING_TEACHER, teacherResponseDto.firstName() + " " + teacherResponseDto.lastName(), requestApiDto.className());
        assertEquals(expectedExceptionMessage, exception.getErrors().get(0).getCause());
    }

    @Test
    public void shouldThrowsBusinessExceptionWhenTryingToSaveNewSchoolClassAndTeacherNotBelongToSchool() {
        // given
        // create teacher
        TeacherRequestApiDto teacherDto = teacherUtil.prepareTeacherRequestDto();
        TeacherResponseApiDto teacherResponseDto = teacherService.register(teacherDto);

        SchoolClassRequestApiDto requestApiDto = schoolClassUtil.prepareSchoolClassRequest(teacherResponseDto.id(), 101L);

        // when
        BusinessException exception = assertThrows(BusinessException.class, () -> schoolClassService.createSchoolClass(requestApiDto));

        // then
        assertEquals(1, exception.getErrors().size());
        assertEquals(SchoolClassValidators.TEACHER_NOT_BELONGS_TO_SCHOOL_VALIDATOR_NAME, exception.getErrors().get(0).getErrorThrownedBy());
        assertEquals(SchoolClassRequestApiDto.ID_CLASS_TEACHER, exception.getErrors().get(0).getField());
        String expectedExceptionMessage = resourceCreator.of(SchoolClassValidators.EXCEPTION_MESSAGE_TEACHER_NOT_BELONG_TO_SCHOOL, teacherResponseDto.firstName() + " " + teacherResponseDto.lastName(), find(School.class, 101L).getName());
        assertEquals(expectedExceptionMessage, exception.getErrors().get(0).getCause());
    }
}
