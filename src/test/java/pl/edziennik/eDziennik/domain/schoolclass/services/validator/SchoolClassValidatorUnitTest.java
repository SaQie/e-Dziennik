package pl.edziennik.eDziennik.domain.schoolclass.services.validator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.edziennik.eDziennik.BaseUnitTest;
import pl.edziennik.eDziennik.domain.personinformation.domain.PersonInformation;
import pl.edziennik.eDziennik.domain.school.domain.School;
import pl.edziennik.eDziennik.domain.school.domain.wrapper.SchoolId;
import pl.edziennik.eDziennik.domain.school.repository.SchoolRepository;
import pl.edziennik.eDziennik.domain.schoolclass.dto.SchoolClassRequestApiDto;
import pl.edziennik.eDziennik.domain.schoolclass.repository.SchoolClassRepository;
import pl.edziennik.eDziennik.domain.student.repository.StudentRepository;
import pl.edziennik.eDziennik.domain.teacher.domain.Teacher;
import pl.edziennik.eDziennik.domain.teacher.domain.wrapper.TeacherId;
import pl.edziennik.eDziennik.domain.teacher.repository.TeacherRepository;
import pl.edziennik.eDziennik.server.basics.dto.ApiValidationResult;
import pl.edziennik.eDziennik.server.utils.ResourceCreator;

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
    private SchoolClassRepository repository;

    @Mock
    private TeacherRepository teacherRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private SchoolRepository schoolRepository;

    @Mock
    private ResourceCreator resourceCreator;

    @Test
    public void shouldReturnApiErrorWhenTryingToAddNewSchoolClassAndGivenTeacherIsAlreadySupervisingTeacher() {
        // given
        SchoolClassRequestApiDto dto = schoolClassUtil.prepareSchoolClassRequest(TeacherId.wrap(1L));

        when(repository.existsByTeacherId(1L)).thenReturn(true);
        when(teacherRepository.findById(TeacherId.wrap(1L))).thenReturn(Optional.of(schoolClassUtil.prepareTeacherForTests()));
        lenient().when(resourceCreator.of(SchoolClassValidators.EXCEPTION_MESSAGE_TEACHER_IS_ALREADY_SUPERVISING_TEACHER, null, null))
                .thenReturn(SchoolClassValidators.EXCEPTION_MESSAGE_TEACHER_IS_ALREADY_SUPERVISING_TEACHER);

        // when
        Optional<ApiValidationResult> validationResult = alreadySupervisingTeacherValidator.validate(dto);

        // then
        assertTrue(validationResult.isPresent());
        ApiValidationResult error = validationResult.get();
        assertEquals(error.getErrorThrownedBy(), SchoolClassValidators.TEACHER_IS_ALREADY_SUPERVISING_TEACHER_VALIDATOR_NAME);
        assertEquals(error.getField(), SchoolClassRequestApiDto.ID_CLASS_TEACHER);
        assertEquals(error.getCause(), getErrorMessage(SchoolClassValidators.EXCEPTION_MESSAGE_TEACHER_IS_ALREADY_SUPERVISING_TEACHER));
    }

    @Test
    public void shouldNotReturnApiErrorWhenTryingToAddNewSchoolClassAndGivenTeacherIsNotSupervisingTeacher() {
        // given
        SchoolClassRequestApiDto dto = schoolClassUtil.prepareSchoolClassRequest(TeacherId.wrap(1L));
        Teacher teacher = new Teacher();
        teacher.setPersonInformation(new PersonInformation());

        when(repository.existsByTeacherId(1L)).thenReturn(false);
        lenient().when(resourceCreator.of(SchoolClassValidators.EXCEPTION_MESSAGE_TEACHER_IS_ALREADY_SUPERVISING_TEACHER, null, null))
                .thenReturn(SchoolClassValidators.EXCEPTION_MESSAGE_TEACHER_IS_ALREADY_SUPERVISING_TEACHER);

        // when
        Optional<ApiValidationResult> validationResult = alreadySupervisingTeacherValidator.validate(dto);

        // then
        assertFalse(validationResult.isPresent());
    }

    @Test
    public void shouldReturnApiErrorWhenGivenTeacherIsFromDifferentSchool() {
        // given
        SchoolClassRequestApiDto dto = schoolClassUtil.prepareSchoolClassRequest(TeacherId.wrap(1L));

        when(repository.existsByTeacherIdAndSchoolId(1L, 100L)).thenReturn(false);
        when(teacherRepository.findById(TeacherId.wrap(1L))).thenReturn(Optional.of(schoolClassUtil.prepareTeacherForTests()));
        when(schoolRepository.findById(SchoolId.wrap(100L))).thenReturn(Optional.of(new School()));
        lenient().when(resourceCreator.of(SchoolClassValidators.EXCEPTION_MESSAGE_TEACHER_NOT_BELONG_TO_SCHOOL, null, null))
                .thenReturn(SchoolClassValidators.EXCEPTION_MESSAGE_TEACHER_NOT_BELONG_TO_SCHOOL);

        // when
        Optional<ApiValidationResult> validationResult = teacherNotBelongsToSchoolValidator.validate(dto);

        // then
        assertTrue(validationResult.isPresent());
        ApiValidationResult error = validationResult.get();
        assertEquals(error.getErrorThrownedBy(), SchoolClassValidators.TEACHER_NOT_BELONGS_TO_SCHOOL_VALIDATOR_NAME);
        assertEquals(error.getField(), SchoolClassRequestApiDto.ID_CLASS_TEACHER);
        assertEquals(error.getCause(), getErrorMessage(SchoolClassValidators.EXCEPTION_MESSAGE_TEACHER_NOT_BELONG_TO_SCHOOL));
    }

    @Test
    public void shouldNotReturnApiErrorWhenGivenTeacherIsFromTheSameSchool(){
        // given
        SchoolClassRequestApiDto dto = schoolClassUtil.prepareSchoolClassRequest(TeacherId.wrap(1L));

        when(repository.existsByTeacherIdAndSchoolId(1L, 100L)).thenReturn(true);
        lenient().when(resourceCreator.of(SchoolClassValidators.EXCEPTION_MESSAGE_TEACHER_NOT_BELONG_TO_SCHOOL, "null null", null))
                .thenReturn(SchoolClassValidators.EXCEPTION_MESSAGE_TEACHER_NOT_BELONG_TO_SCHOOL);

        // when
        Optional<ApiValidationResult> validationResult = teacherNotBelongsToSchoolValidator.validate(dto);

        // then
        assertFalse(validationResult.isPresent());
    }


    @Test
    public void shouldReturnApiErrorWhenSchoolClassAlreadyExists(){
        // given
        SchoolClassRequestApiDto dto = schoolClassUtil.prepareSchoolClassRequest(TeacherId.wrap(1L));

        when(repository.existsByClassNameAndSchoolId("3B", 100L)).thenReturn(true);
        when(schoolRepository.findById(SchoolId.wrap(100L))).thenReturn(Optional.of(new School()));
        lenient().when(resourceCreator.of(SchoolClassValidators.EXCEPTION_MESSAGE_SCHOOL_CLASS_ALREADY_EXIST, "3B", null))
                .thenReturn(SchoolClassValidators.EXCEPTION_MESSAGE_SCHOOL_CLASS_ALREADY_EXIST);

        // when
        Optional<ApiValidationResult> validationResult = existsValidator.validate(dto);

        // then
        assertTrue(validationResult.isPresent());
        ApiValidationResult error = validationResult.get();
        assertEquals(error.getErrorThrownedBy(), SchoolClassValidators.SCHOOL_CLASS_ALREADY_EXISTS_VALIDATOR_NAME);
        assertEquals(error.getField(), SchoolClassRequestApiDto.CLASS_NAME);
        assertEquals(error.getCause(), getErrorMessage(SchoolClassValidators.EXCEPTION_MESSAGE_SCHOOL_CLASS_ALREADY_EXIST));
    }

    @Test
    public void shouldNotReturnApiErrorWhenSchoolClassNotExists(){
        // given
        SchoolClassRequestApiDto dto = schoolClassUtil.prepareSchoolClassRequest(TeacherId.wrap(1L));

        when(repository.existsByClassNameAndSchoolId("3B", 100L)).thenReturn(false);
        lenient().when(resourceCreator.of(SchoolClassValidators.EXCEPTION_MESSAGE_SCHOOL_CLASS_ALREADY_EXIST, "3B", null))
                .thenReturn(SchoolClassValidators.EXCEPTION_MESSAGE_SCHOOL_CLASS_ALREADY_EXIST);

        // when
        Optional<ApiValidationResult> validationResult = existsValidator.validate(dto);

        // then
        assertFalse(validationResult.isPresent());
    }

}
