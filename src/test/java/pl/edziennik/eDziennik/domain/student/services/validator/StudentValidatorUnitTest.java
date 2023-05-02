package pl.edziennik.eDziennik.domain.student.services.validator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.edziennik.eDziennik.BaseUnitTest;
import pl.edziennik.eDziennik.domain.personinformation.domain.wrapper.Pesel;
import pl.edziennik.eDziennik.domain.role.domain.Role;
import pl.edziennik.eDziennik.domain.school.domain.School;
import pl.edziennik.eDziennik.domain.school.repository.SchoolRepository;
import pl.edziennik.eDziennik.domain.schoolclass.domain.SchoolClass;
import pl.edziennik.eDziennik.domain.schoolclass.repository.SchoolClassRepository;
import pl.edziennik.eDziennik.domain.student.dto.StudentRequestApiDto;
import pl.edziennik.eDziennik.domain.student.repository.StudentRepository;
import pl.edziennik.eDziennik.server.basic.dto.ApiValidationResult;
import pl.edziennik.eDziennik.server.utils.ResourceCreator;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StudentValidatorUnitTest extends BaseUnitTest {

    @InjectMocks
    private StudentPeselNotUniqueValidator peselNotUniqueValidator;

    @InjectMocks
    private StudentSchoolClassNotBelongsToSchoolValidator belongsToSchoolValidator;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private SchoolRepository schoolRepository;

    @Mock
    private SchoolClassRepository schoolClassRepository;

    @Mock
    private ResourceCreator resourceCreator;


    @Test
    public void shouldReturnApiErrorWhenStudentPeselNotUnique() {
        // given
        StudentRequestApiDto request = studentUtil.prepareStudentRequestDto();
        when(studentRepository.isStudentExistsByPesel(Pesel.of(request.pesel()), Role.RoleConst.ROLE_STUDENT.getId())).thenReturn(true);
        lenient().when(resourceCreator.of(StudentValidators.EXCEPTION_MESSAGE_PESEL_NOT_UNIQUE, request.pesel()))
                .thenReturn(StudentValidators.EXCEPTION_MESSAGE_PESEL_NOT_UNIQUE);

        // when
        Optional<ApiValidationResult> validationResult = peselNotUniqueValidator.validate(request);

        // then
        assertTrue(validationResult.isPresent());
        ApiValidationResult error = validationResult.get();
        assertEquals(error.getErrorThrownedBy(), StudentValidators.STUDENT_PESEL_NOT_UNIQUE_VALIDATOR_NAME);
        assertEquals(error.getField(), StudentRequestApiDto.PESEL);
        assertEquals(error.getCause(), getErrorMessage(StudentValidators.EXCEPTION_MESSAGE_PESEL_NOT_UNIQUE));
    }

    @Test
    public void shouldNotReturnApiErrorWhenStudentPeselIsUnique() {
        // given
        StudentRequestApiDto request = studentUtil.prepareStudentRequestDto();
        when(studentRepository.isStudentExistsByPesel(Pesel.of(request.pesel()), Role.RoleConst.ROLE_STUDENT.getId())).thenReturn(false);

        // when
        Optional<ApiValidationResult> validationResult = peselNotUniqueValidator.validate(request);

        // then
        assertFalse(validationResult.isPresent());
    }

    @Test
    public void shouldReturnApiErrorWhenSelectedStudentSchoolClassNotBelongsToSchool() {
        // given
        StudentRequestApiDto request = studentUtil.prepareStudentRequestDto();
        SchoolClass schoolClass = new SchoolClass();
        School school = new School();
        school.setSchoolClasses(Collections.emptyList());

        when(schoolRepository.findById(request.schoolId())).thenReturn(Optional.of(school));
        when(schoolClassRepository.findById(request.schoolClassId())).thenReturn(Optional.of(schoolClass));
        lenient().when(resourceCreator.of(StudentValidators.EXCEPTION_MESSAGE_SCHOOL_CLASS_NOT_BELONG_TO_SCHOOL, null, null))
                .thenReturn(StudentValidators.EXCEPTION_MESSAGE_SCHOOL_CLASS_NOT_BELONG_TO_SCHOOL);

        // when
        Optional<ApiValidationResult> validationResult = belongsToSchoolValidator.validate(request);

        // then
        assertTrue(validationResult.isPresent());
        ApiValidationResult error = validationResult.get();
        assertEquals(error.getErrorThrownedBy(), StudentValidators.STUDENT_SCHOOL_CLASS_NOT_BELONGS_TO_SCHOOL_VALIDATOR_NAME);
        assertEquals(error.getCause(), getErrorMessage(StudentValidators.EXCEPTION_MESSAGE_SCHOOL_CLASS_NOT_BELONG_TO_SCHOOL));
    }

    @Test
    public void shouldNotReturnApiErrorWhenSelectedStudentSchoolClassBelongsToSchool() {
        // given
        StudentRequestApiDto request = studentUtil.prepareStudentRequestDto();
        SchoolClass schoolClass = new SchoolClass();
        School school = new School();
        school.setSchoolClasses(List.of(schoolClass));

        when(schoolRepository.findById(request.schoolId())).thenReturn(Optional.of(school));
        when(schoolClassRepository.findById(request.schoolClassId())).thenReturn(Optional.of(schoolClass));

        // when
        Optional<ApiValidationResult> validationResult = belongsToSchoolValidator.validate(request);

        // then
        assertFalse(validationResult.isPresent());
    }

}
