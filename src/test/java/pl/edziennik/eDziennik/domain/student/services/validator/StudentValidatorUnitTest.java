package pl.edziennik.eDziennik.domain.student.services.validator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.edziennik.eDziennik.BaseUnitTest;
import pl.edziennik.eDziennik.domain.parent.domain.Parent;
import pl.edziennik.eDziennik.domain.personinformation.domain.PersonInformation;
import pl.edziennik.eDziennik.domain.school.dao.SchoolDao;
import pl.edziennik.eDziennik.domain.school.domain.School;
import pl.edziennik.eDziennik.domain.schoolclass.domain.SchoolClass;
import pl.edziennik.eDziennik.domain.student.dao.StudentDao;
import pl.edziennik.eDziennik.domain.student.domain.Student;
import pl.edziennik.eDziennik.domain.student.dto.StudentRequestApiDto;
import pl.edziennik.eDziennik.server.basics.dto.ApiErrorDto;
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
    private StudentStillHasParentValidator hasParentValidator;

    @InjectMocks
    private StudentSchoolClassNotBelongsToSchoolValidator belongsToSchoolValidator;

    @Mock
    private StudentDao dao;

    @Mock
    private SchoolDao schoolDao;

    @Mock
    private ResourceCreator resourceCreator;


    @Test
    public void shouldReturnApiErrorWhenStudentPeselNotUnique() {
        // given
        StudentRequestApiDto request = studentUtil.prepareStudentRequestDto();
        when(dao.isStudentExistsByPesel(request.getPesel())).thenReturn(true);
        lenient().when(resourceCreator.of(StudentValidators.EXCEPTION_MESSAGE_PESEL_NOT_UNIQUE, request.getPesel()))
                .thenReturn(StudentValidators.EXCEPTION_MESSAGE_PESEL_NOT_UNIQUE);

        // when
        Optional<ApiErrorDto> validationResult = peselNotUniqueValidator.validate(request);

        // then
        assertTrue(validationResult.isPresent());
        ApiErrorDto error = validationResult.get();
        assertEquals(error.getErrorThrownedBy(), StudentValidators.STUDENT_PESEL_NOT_UNIQUE_VALIDATOR_NAME);
        assertEquals(error.getField(), StudentRequestApiDto.PESEL);
        assertEquals(error.getCause(), getErrorMessage(StudentValidators.EXCEPTION_MESSAGE_PESEL_NOT_UNIQUE));
    }

    @Test
    public void shouldNotReturnApiErrorWhenStudentPeselIsUnique() {
        // given
        StudentRequestApiDto request = studentUtil.prepareStudentRequestDto();
        when(dao.isStudentExistsByPesel(request.getPesel())).thenReturn(false);

        // when
        Optional<ApiErrorDto> validationResult = peselNotUniqueValidator.validate(request);

        // then
        assertFalse(validationResult.isPresent());
    }

    @Test
    public void shouldReturnApiErrorWhenDeleteAndStudentHasParent() {
        // given
        StudentRequestApiDto request = studentUtil.prepareStudentRequestDto();
        Student student = new Student();
        student.setParent(new Parent());
        student.setPersonInformation(new PersonInformation());

        when(dao.getByUsername(request.getUsername())).thenReturn(student);
        lenient().when(resourceCreator.of(StudentValidators.EXCEPTION_MESSAGE_STUDENT_STILL_HAS_PARENT, null))
                .thenReturn(StudentValidators.EXCEPTION_MESSAGE_STUDENT_STILL_HAS_PARENT);

        // when
        Optional<ApiErrorDto> validationResult = hasParentValidator.validate(request);

        // then
        assertTrue(validationResult.isPresent());
        ApiErrorDto error = validationResult.get();
        assertEquals(error.getErrorThrownedBy(), StudentValidators.STUDENT_STILL_HAS_PARENT_VALIDATOR);
        assertEquals(error.getCause(), getErrorMessage(StudentValidators.EXCEPTION_MESSAGE_STUDENT_STILL_HAS_PARENT));
    }

    @Test
    public void shouldNotReturnApiErrorWhenDeleteAndStudentDoesNotHaveParent() {
        // given
        StudentRequestApiDto request = studentUtil.prepareStudentRequestDto();
        Student student = new Student();
        student.setPersonInformation(new PersonInformation());

        when(dao.getByUsername(request.getUsername())).thenReturn(student);

        // when
        Optional<ApiErrorDto> validationResult = hasParentValidator.validate(request);

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

        when(schoolDao.get(request.getIdSchool())).thenReturn(school);
        when(schoolDao.get(SchoolClass.class, request.getIdSchoolClass())).thenReturn(schoolClass);
        lenient().when(resourceCreator.of(StudentValidators.EXCEPTION_MESSAGE_SCHOOL_CLASS_NOT_BELONG_TO_SCHOOL, null, null))
                .thenReturn(StudentValidators.EXCEPTION_MESSAGE_SCHOOL_CLASS_NOT_BELONG_TO_SCHOOL);

        // when
        Optional<ApiErrorDto> validationResult = belongsToSchoolValidator.validate(request);

        // then
        assertTrue(validationResult.isPresent());
        ApiErrorDto error = validationResult.get();
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

        when(schoolDao.get(request.getIdSchool())).thenReturn(school);
        when(schoolDao.get(SchoolClass.class, request.getIdSchoolClass())).thenReturn(schoolClass);

        // when
        Optional<ApiErrorDto> validationResult = belongsToSchoolValidator.validate(request);

        // then
        assertFalse(validationResult.isPresent());
    }

}
