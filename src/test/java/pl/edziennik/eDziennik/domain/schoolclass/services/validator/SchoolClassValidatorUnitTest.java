package pl.edziennik.eDziennik.domain.schoolclass.services.validator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.edziennik.eDziennik.BaseUnitTest;
import pl.edziennik.eDziennik.domain.admin.dao.AdminDao;
import pl.edziennik.eDziennik.domain.admin.domain.Admin;
import pl.edziennik.eDziennik.domain.admin.dto.AdminRequestApiDto;
import pl.edziennik.eDziennik.domain.admin.services.validator.AdminValidators;
import pl.edziennik.eDziennik.domain.school.domain.School;
import pl.edziennik.eDziennik.domain.schoolclass.SchoolClassIntergrationTestUtil;
import pl.edziennik.eDziennik.domain.schoolclass.dao.SchoolClassDao;
import pl.edziennik.eDziennik.domain.schoolclass.dto.SchoolClassRequestApiDto;
import pl.edziennik.eDziennik.domain.studentsubject.dto.request.StudentSubjectRequestDto;
import pl.edziennik.eDziennik.domain.teacher.domain.Teacher;
import pl.edziennik.eDziennik.server.basics.dto.ApiErrorDto;
import pl.edziennik.eDziennik.server.utils.ResourceCreator;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SchoolClassValidatorUnitTest extends BaseUnitTest {

    @InjectMocks
    private SchoolClassAlreadyExistsValidator existsValidator;

    @InjectMocks
    private TeacherIsAlreadySupervisingTeacherValidator alreadySupervisingTeacherValidator;

    @InjectMocks
    private TeacherNotBelongsToSchoolValidator teacherNotBelongsToSchoolValidator;

    @Mock
    private SchoolClassDao dao;

    @Mock
    private ResourceCreator resourceCreator;

    @Test
    public void shouldReturnApiErrorWhenTryingToAddNewSchoolClassAndGivenTeacherIsAlreadySupervisingTeacher() {
        // given
        SchoolClassRequestApiDto dto = schoolClassUtil.prepareSchoolClassRequest(1L);

        when(dao.isTeacherAlreadySupervisingTeacher(1L)).thenReturn(true);
        when(dao.get(Teacher.class, 1L)).thenReturn(schoolClassUtil.prepareTeacherForTests());
        lenient().when(resourceCreator.of(SchoolClassValidators.EXCEPTION_MESSAGE_TEACHER_IS_ALREADY_SUPERVISING_TEACHER, "null null", null))
                .thenReturn(SchoolClassValidators.EXCEPTION_MESSAGE_TEACHER_IS_ALREADY_SUPERVISING_TEACHER);

        // when
        Optional<ApiErrorDto> validationResult = alreadySupervisingTeacherValidator.validate(dto);

        // then
        assertTrue(validationResult.isPresent());
        ApiErrorDto error = validationResult.get();
        assertEquals(error.getErrorThrownedBy(), SchoolClassValidators.TEACHER_IS_ALREADY_SUPERVISING_TEACHER_VALIDATOR_NAME);
        assertEquals(error.getField(), SchoolClassRequestApiDto.ID_CLASS_TEACHER);
        assertEquals(error.getCause(), getErrorMessage(SchoolClassValidators.EXCEPTION_MESSAGE_TEACHER_IS_ALREADY_SUPERVISING_TEACHER));
    }

    @Test
    public void shouldNotReturnApiErrorWhenTryingToAddNewSchoolClassAndGivenTeacherIsNotSupervisingTeacher() {
        // given
        SchoolClassRequestApiDto dto = schoolClassUtil.prepareSchoolClassRequest(1L);

        when(dao.isTeacherAlreadySupervisingTeacher(1L)).thenReturn(false);
        lenient().when(resourceCreator.of(SchoolClassValidators.EXCEPTION_MESSAGE_TEACHER_IS_ALREADY_SUPERVISING_TEACHER, "null null", null))
                .thenReturn(SchoolClassValidators.EXCEPTION_MESSAGE_TEACHER_IS_ALREADY_SUPERVISING_TEACHER);

        // when
        Optional<ApiErrorDto> validationResult = alreadySupervisingTeacherValidator.validate(dto);

        // then
        assertFalse(validationResult.isPresent());
    }

    @Test
    public void shouldReturnApiErrorWhenGivenTeacherIsFromDifferentSchool() {
        // given
        SchoolClassRequestApiDto dto = schoolClassUtil.prepareSchoolClassRequest(1L);

        when(dao.isTeacherBelongsToSchool(1L, 100L)).thenReturn(false);
        when(dao.get(Teacher.class, 1L)).thenReturn(schoolClassUtil.prepareTeacherForTests());
        when(dao.get(School.class, 100L)).thenReturn(new School());
        lenient().when(resourceCreator.of(SchoolClassValidators.EXCEPTION_MESSAGE_TEACHER_NOT_BELONG_TO_SCHOOL, "null null", null))
                .thenReturn(SchoolClassValidators.EXCEPTION_MESSAGE_TEACHER_NOT_BELONG_TO_SCHOOL);

        // when
        Optional<ApiErrorDto> validationResult = teacherNotBelongsToSchoolValidator.validate(dto);

        // then
        assertTrue(validationResult.isPresent());
        ApiErrorDto error = validationResult.get();
        assertEquals(error.getErrorThrownedBy(), SchoolClassValidators.TEACHER_NOT_BELONGS_TO_SCHOOL_VALIDATOR_NAME);
        assertEquals(error.getField(), SchoolClassRequestApiDto.ID_CLASS_TEACHER);
        assertEquals(error.getCause(), getErrorMessage(SchoolClassValidators.EXCEPTION_MESSAGE_TEACHER_NOT_BELONG_TO_SCHOOL));
    }

    @Test
    public void shouldNotReturnApiErrorWhenGivenTeacherIsFromTheSameSchool(){
        // given
        SchoolClassRequestApiDto dto = schoolClassUtil.prepareSchoolClassRequest(1L);

        when(dao.isTeacherBelongsToSchool(1L, 100L)).thenReturn(true);
        lenient().when(resourceCreator.of(SchoolClassValidators.EXCEPTION_MESSAGE_TEACHER_NOT_BELONG_TO_SCHOOL, "null null", null))
                .thenReturn(SchoolClassValidators.EXCEPTION_MESSAGE_TEACHER_NOT_BELONG_TO_SCHOOL);

        // when
        Optional<ApiErrorDto> validationResult = teacherNotBelongsToSchoolValidator.validate(dto);

        // then
        assertFalse(validationResult.isPresent());
    }


    @Test
    public void shouldReturnApiErrorWhenSchoolClassAlreadyExists(){
        // given
        SchoolClassRequestApiDto dto = schoolClassUtil.prepareSchoolClassRequest(1L);

        when(dao.isSchoolClassAlreadyExist("3B", 100L)).thenReturn(true);
        when(dao.get(School.class, 100L)).thenReturn(new School());
        lenient().when(resourceCreator.of(SchoolClassValidators.EXCEPTION_MESSAGE_SCHOOL_CLASS_ALREADY_EXIST, "3B", null))
                .thenReturn(SchoolClassValidators.EXCEPTION_MESSAGE_SCHOOL_CLASS_ALREADY_EXIST);

        // when
        Optional<ApiErrorDto> validationResult = existsValidator.validate(dto);

        // then
        assertTrue(validationResult.isPresent());
        ApiErrorDto error = validationResult.get();
        assertEquals(error.getErrorThrownedBy(), SchoolClassValidators.SCHOOL_CLASS_ALREADY_EXISTS_VALIDATOR_NAME);
        assertEquals(error.getField(), SchoolClassRequestApiDto.CLASS_NAME);
        assertEquals(error.getCause(), getErrorMessage(SchoolClassValidators.EXCEPTION_MESSAGE_SCHOOL_CLASS_ALREADY_EXIST));
    }

    @Test
    public void shouldNotReturnApiErrorWhenSchoolClassNotExists(){
        // given
        SchoolClassRequestApiDto dto = schoolClassUtil.prepareSchoolClassRequest(1L);

        when(dao.isSchoolClassAlreadyExist("3B", 100L)).thenReturn(false);
        lenient().when(resourceCreator.of(SchoolClassValidators.EXCEPTION_MESSAGE_SCHOOL_CLASS_ALREADY_EXIST, "3B", null))
                .thenReturn(SchoolClassValidators.EXCEPTION_MESSAGE_SCHOOL_CLASS_ALREADY_EXIST);

        // when
        Optional<ApiErrorDto> validationResult = existsValidator.validate(dto);

        // then
        assertFalse(validationResult.isPresent());
    }

}
