package pl.edziennik.eDziennik.domain.schoolclass;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import pl.edziennik.eDziennik.BaseTesting;
import pl.edziennik.eDziennik.domain.school.domain.School;
import pl.edziennik.eDziennik.domain.school.domain.wrapper.SchoolId;
import pl.edziennik.eDziennik.domain.schoolclass.domain.SchoolClass;
import pl.edziennik.eDziennik.domain.schoolclass.domain.wrapper.SchoolClassId;
import pl.edziennik.eDziennik.domain.schoolclass.dto.SchoolClassRequestApiDto;
import pl.edziennik.eDziennik.domain.schoolclass.dto.SchoolClassResponseApiDto;
import pl.edziennik.eDziennik.domain.schoolclass.services.validator.SchoolClassValidators;
import pl.edziennik.eDziennik.domain.teacher.domain.Teacher;
import pl.edziennik.eDziennik.domain.teacher.domain.wrapper.TeacherId;
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
        SchoolClassId id = schoolClassService.createSchoolClass(expected).schoolClassId();

        // then
        assertNotNull(id);
        SchoolClass actual = find(SchoolClass.class, id);
        assertEquals(expected.schoolId(), actual.getSchool().getSchoolId());
        assertNull(actual.getTeacher());
        assertEquals(expected.className(), actual.getClassName());
    }

    @Test
    public void shouldUpdateSchoolClass() {
        // given
        TeacherRequestApiDto teacherRequest = teacherUtil.prepareTeacherRequestDto();
        TeacherId teacherId = teacherService.register(teacherRequest).teacherId();
        SchoolClassRequestApiDto dto = schoolClassUtil.prepareSchoolClassRequest();
        SchoolClassId id = schoolClassService.createSchoolClass(dto).schoolClassId();
        SchoolClassRequestApiDto expected = schoolClassUtil.prepareSchoolClassRequest("5B", teacherId);

        // when
        SchoolClassId updated = schoolClassService.updateSchoolClass(id, expected).schoolClassId();

        // then
        assertNotNull(updated);
        assertEquals(id, updated);

        SchoolClass actual = find(SchoolClass.class, updated);
        assertEquals(expected.schoolId(), actual.getSchool().getSchoolId());
        assertEquals(expected.idClassTeacher(), actual.getTeacher().getTeacherId());
        assertEquals(expected.className(), actual.getClassName());

    }

    @Test
    public void shouldDeteleSchoolClass() {
        // given
        SchoolClassRequestApiDto expected = schoolClassUtil.prepareSchoolClassRequest();
        SchoolClassId schoolClassId = schoolClassService.createSchoolClass(expected).schoolClassId();
        assertNotNull(schoolClassId);

        // when
        schoolClassService.deleteSchoolClassById(schoolClassId);

        // then
        SchoolClass schoolClass = find(SchoolClass.class, schoolClassId);
        assertNull(schoolClass);
    }

    @Test
    public void shouldFindSchoolClassById() {
        // given
        SchoolClassRequestApiDto expected = schoolClassUtil.prepareSchoolClassRequest();
        SchoolClassId schoolClassId = schoolClassService.createSchoolClass(expected).schoolClassId();
        assertNotNull(schoolClassId);

        // when
        SchoolClassResponseApiDto actual = schoolClassService.findSchoolClassById(schoolClassId);

        // then
        assertNotNull(actual);
        assertEquals(expected.schoolId(), actual.school().schoolId());
        assertNull(actual.supervisingTeacher());
        assertEquals(expected.className(), actual.className());
    }

    @Test
    public void shouldThrowsExceptionWhenTryingToSaveSchoolClassIfTeacherNotExist() {
        // given
        TeacherId teacherId = TeacherId.wrap(222L);
        SchoolClassRequestApiDto expected = schoolClassUtil.prepareSchoolClassRequest(teacherId);

        // when

        Exception exception = assertThrows(EntityNotFoundException.class, () -> schoolClassService.createSchoolClass(expected));

        // then
        assertEquals(exception.getMessage(), resourceCreator.of("not.found.message", teacherId.id(), Teacher.class.getSimpleName()));

    }

    @Test
    public void shouldThrowsExceptionWhenTryingToSaveSchoolClassIfSchoolNotExist() {
        // given
        TeacherId idTeacher = TeacherId.wrap(1L);
        TeacherRequestApiDto teacherRequest = teacherUtil.prepareTeacherRequestDto();
        TeacherId teacherId = teacherService.register(teacherRequest).teacherId();
        assertNotNull(teacherId);

        SchoolId schoolId = SchoolId.wrap(222L);
        SchoolClassRequestApiDto expected = schoolClassUtil.prepareSchoolClassRequest(idTeacher, schoolId);

        // when
        Exception exception = assertThrows(EntityNotFoundException.class, () -> schoolClassService.createSchoolClass(expected));

        // then
        assertEquals(exception.getMessage(), resourceCreator.of("not.found.message", schoolId.id(), School.class.getSimpleName()));
    }

    @Test
    public void shouldThrowsBusinessExceptionWhenTryingToSaveNewSchoolClassAndSchoolClassAlreadyExist() {
        // given
        SchoolClassRequestApiDto requestApiDto = schoolClassUtil.prepareSchoolClassRequest();
        SchoolClassId id = schoolClassService.createSchoolClass(requestApiDto).schoolClassId();
        assertNotNull(id);

        // when
        SchoolClassRequestApiDto requestApiDto2 = schoolClassUtil.prepareSchoolClassRequest();
        BusinessException exception = assertThrows(BusinessException.class, () -> schoolClassService.createSchoolClass(requestApiDto2));

        // then
        assertEquals(1, exception.getErrors().size());
        assertEquals(SchoolClassValidators.SCHOOL_CLASS_ALREADY_EXISTS_VALIDATOR_NAME, exception.getErrors().get(0).getErrorThrownedBy());
        assertEquals(SchoolClassRequestApiDto.CLASS_NAME, exception.getErrors().get(0).getField());
        String expectedExceptionMessage = resourceCreator.of(SchoolClassValidators.EXCEPTION_MESSAGE_SCHOOL_CLASS_ALREADY_EXIST, requestApiDto.className(), find(School.class, requestApiDto.schoolId()).getName());
        assertEquals(expectedExceptionMessage, exception.getErrors().get(0).getCause());
    }

    @Test
    public void shouldThrowsBusinessExceptionWhenTryingToSaveNewSchoolClassAndTeacherIsAlreadySupervisingTeacher() {
        // given
        // create teacher
        TeacherRequestApiDto teacherDto = teacherUtil.prepareTeacherRequestDto();
        TeacherResponseApiDto teacherResponseDto = teacherService.register(teacherDto);

        // create school class with teacher
        SchoolClassRequestApiDto requestApiDto = schoolClassUtil.prepareSchoolClassRequest(teacherResponseDto.teacherId());
        SchoolClassId id = schoolClassService.createSchoolClass(requestApiDto).schoolClassId();
        assertNotNull(id);

        // create second school class with the same teacher
        SchoolClassRequestApiDto schoolClassRequestApiDto = schoolClassUtil.prepareSchoolClassRequest("9B", teacherResponseDto.teacherId());

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

        SchoolClassRequestApiDto requestApiDto = schoolClassUtil.prepareSchoolClassRequest(teacherResponseDto.teacherId(), SchoolId.wrap(101L));

        // when
        BusinessException exception = assertThrows(BusinessException.class, () -> schoolClassService.createSchoolClass(requestApiDto));

        // then
        assertEquals(1, exception.getErrors().size());
        assertEquals(SchoolClassValidators.TEACHER_NOT_BELONGS_TO_SCHOOL_VALIDATOR_NAME, exception.getErrors().get(0).getErrorThrownedBy());
        assertEquals(SchoolClassRequestApiDto.ID_CLASS_TEACHER, exception.getErrors().get(0).getField());
        String expectedExceptionMessage = resourceCreator.of(SchoolClassValidators.EXCEPTION_MESSAGE_TEACHER_NOT_BELONG_TO_SCHOOL, teacherResponseDto.firstName() + " " + teacherResponseDto.lastName(), find(School.class, SchoolId.wrap(101L)).getName());
        assertEquals(expectedExceptionMessage, exception.getErrors().get(0).getCause());
    }
}
