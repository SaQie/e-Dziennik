package pl.edziennik.eDziennik.domain.parent.services.validator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;
import pl.edziennik.eDziennik.BaseUnitTest;
import pl.edziennik.eDziennik.domain.parent.dao.ParentDao;
import pl.edziennik.eDziennik.domain.parent.domain.Parent;
import pl.edziennik.eDziennik.domain.parent.domain.dto.ParentRequestApiDto;
import pl.edziennik.eDziennik.domain.personinformation.domain.PersonInformation;
import pl.edziennik.eDziennik.domain.schoolclass.dto.SchoolClassRequestApiDto;
import pl.edziennik.eDziennik.domain.schoolclass.services.validator.SchoolClassValidators;
import pl.edziennik.eDziennik.domain.student.domain.Student;
import pl.edziennik.eDziennik.domain.student.dto.StudentRequestApiDto;
import pl.edziennik.eDziennik.domain.student.services.validator.StudentValidators;
import pl.edziennik.eDziennik.server.basics.dto.ApiErrorDto;
import pl.edziennik.eDziennik.server.utils.ResourceCreator;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ParentValidatorUnitTest extends BaseUnitTest {


    @InjectMocks
    private ParentPeselNotUniqueValidator peselNotUniqueValidator;

    @InjectMocks
    private StudentAlreadyHasParentValidator hasParentValidator;

    @InjectMocks
    private ParentStillHasStudentValidator parentStillHasStudentValidator;

    @Mock
    private ResourceCreator resourceCreator;

    @Mock
    private ParentDao parentDao;

    @Test
    public void shouldReturnApiErrorWhenStudentAlreadyHasParent() {
        // given
        ParentRequestApiDto parentRequestApiDto = parentUtil.prepareParentRequestApiDto(1L);
        Student student = new Student();
        Parent parent = new Parent();
        PersonInformation personInformation = new PersonInformation();
        student.setParent(parent);
        student.setPersonInformation(personInformation);

        when(parentDao.get(Student.class, parentRequestApiDto.getIdStudent())).thenReturn(student);
        lenient().when(resourceCreator.of(ParentValidators.EXCEPTION_MESSAGE_STUDENT_ALREADY_HAS_PARENT_VALIDATOR, null))
                .thenReturn(ParentValidators.EXCEPTION_MESSAGE_STUDENT_ALREADY_HAS_PARENT_VALIDATOR);

        // when
        Optional<ApiErrorDto> validationResult = hasParentValidator.validate(parentRequestApiDto);

        // then
        assertTrue(validationResult.isPresent());
        ApiErrorDto error = validationResult.get();
        assertEquals(error.getErrorThrownedBy(), ParentValidators.STUDENT_ALREADY_HAS_PARENT_VALIDATOR);
        assertEquals(error.getField(), ParentRequestApiDto.ID_STUDENT);
        assertEquals(error.getCause(), getErrorMessage(ParentValidators.EXCEPTION_MESSAGE_STUDENT_ALREADY_HAS_PARENT_VALIDATOR));

    }

    @Test
    public void shouldNotReturnApiErrorWhenStudentDoesNotHaveParent() {
        // given
        ParentRequestApiDto parentRequestApiDto = parentUtil.prepareParentRequestApiDto(1L);
        Student student = new Student();
        PersonInformation personInformation = new PersonInformation();
        student.setPersonInformation(personInformation);

        when(parentDao.get(Student.class, parentRequestApiDto.getIdStudent())).thenReturn(student);
        lenient().when(resourceCreator.of(ParentValidators.EXCEPTION_MESSAGE_STUDENT_ALREADY_HAS_PARENT_VALIDATOR, null))
                .thenReturn(ParentValidators.EXCEPTION_MESSAGE_STUDENT_ALREADY_HAS_PARENT_VALIDATOR);

        // when
        Optional<ApiErrorDto> validationResult = hasParentValidator.validate(parentRequestApiDto);

        // then
        assertFalse(validationResult.isPresent());
    }

    @Test
    public void shouldReturnApiErrorWhenDeleteAndParentHasStudent() {
        // given
        Parent parent = new Parent();
        Student student = new Student();
        parent.setStudent(student);
        student.setPersonInformation(new PersonInformation());

        when(parentDao.get(1L)).thenReturn(parent);
        lenient().when(resourceCreator.of(ParentValidators.EXCEPTON_MESSAGE_PARENT_STILL_HAS_STUDENT, null))
                .thenReturn(ParentValidators.EXCEPTON_MESSAGE_PARENT_STILL_HAS_STUDENT);

        // when
        Optional<ApiErrorDto> validationResult = parentStillHasStudentValidator.validate(1L);

        // then
        assertTrue(validationResult.isPresent());
        ApiErrorDto error = validationResult.get();
        assertEquals(error.getErrorThrownedBy(), ParentValidators.PARENT_STILL_HAS_STUDENT_VALIDATOR);
        assertEquals(error.getCause(), getErrorMessage(ParentValidators.EXCEPTON_MESSAGE_PARENT_STILL_HAS_STUDENT));
    }

    @Test
    public void shouldNotReturnApiErrorWhenDeleteAndStudentDoesNotHaveParent() {
        // given
        Parent parent = new Parent();

        when(parentDao.get(1L)).thenReturn(parent);

        // when
        Optional<ApiErrorDto> validationResult = parentStillHasStudentValidator.validate(1L);

        // then
        assertFalse(validationResult.isPresent());
    }

    @Test
    public void shouldReturnApiErrorWhenParentPeselNotUnique() {
        // given
        ParentRequestApiDto parentRequestApiDto = parentUtil.prepareParentRequestApiDto(1L);

        when(parentDao.isParentExistsByPesel(parentRequestApiDto.getPesel())).thenReturn(true);
        lenient().when(resourceCreator.of(ParentValidators.EXCEPTION_MESSAGE_PARENT_PESEL_ALREADY_EXISTS, parentRequestApiDto.getPesel()))
                .thenReturn(ParentValidators.EXCEPTION_MESSAGE_PARENT_PESEL_ALREADY_EXISTS);

        // when
        Optional<ApiErrorDto> validationResult = peselNotUniqueValidator.validate(parentRequestApiDto);

        // then
        assertTrue(validationResult.isPresent());
        ApiErrorDto error = validationResult.get();
        assertEquals(error.getErrorThrownedBy(), ParentValidators.PARENT_PESEL_NOT_UNIQUE_VALIDATOR);
        assertEquals(error.getField(), ParentRequestApiDto.PESEL);
        assertEquals(error.getCause(), getErrorMessage(ParentValidators.EXCEPTION_MESSAGE_PARENT_PESEL_ALREADY_EXISTS));
    }

    @Test
    public void shouldNotReturnApiErrorWhenParentPeselIsUnique() {
        // given
        ParentRequestApiDto parentRequestApiDto = parentUtil.prepareParentRequestApiDto(1L);

        when(parentDao.isParentExistsByPesel(parentRequestApiDto.getPesel())).thenReturn(false);
        lenient().when(resourceCreator.of(ParentValidators.EXCEPTION_MESSAGE_PARENT_PESEL_ALREADY_EXISTS, parentRequestApiDto.getPesel()))
                .thenReturn(ParentValidators.EXCEPTION_MESSAGE_PARENT_PESEL_ALREADY_EXISTS);

        // when
        Optional<ApiErrorDto> validationResult = peselNotUniqueValidator.validate(parentRequestApiDto);

        // then
        assertFalse(validationResult.isPresent());
    }

}
