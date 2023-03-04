package pl.edziennik.eDziennik.domain.studentsubject.services.validator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;
import pl.edziennik.eDziennik.BaseUnitTest;
import pl.edziennik.eDziennik.domain.student.domain.Student;
import pl.edziennik.eDziennik.domain.studentsubject.StudentSubjectIntegrationTestUtil;
import pl.edziennik.eDziennik.domain.studentsubject.dao.StudentSubjectDao;
import pl.edziennik.eDziennik.domain.studentsubject.dto.request.StudentSubjectRequestDto;
import pl.edziennik.eDziennik.domain.subject.domain.Subject;
import pl.edziennik.eDziennik.domain.subject.dto.SubjectRequestApiDto;
import pl.edziennik.eDziennik.domain.subject.services.validator.SubjectValidators;
import pl.edziennik.eDziennik.server.basics.dto.ApiErrorDto;
import pl.edziennik.eDziennik.server.utils.ResourceCreator;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class StudentSubjectValidatorUnitTest extends BaseUnitTest {

    @InjectMocks
    private StudentCannotBeAssignedToSubjectFromDifferentClassValidator validator;

    @InjectMocks
    private StudentSubjectAlreadyExistValidator existValidator;

    @Mock
    private StudentSubjectDao dao;

    @Mock
    private ResourceCreator resourceCreator;

    @Test
    public void shouldReturnApiErrorWhenStudentSubjectAlreadyExists() {
        // given
        StudentSubjectRequestDto dto = studentSubjectUtil.prepareStudentSubjectRequestDto(1L, 1L);

        when(dao.isStudentSubjectAlreadyExist(1L, 1L)).thenReturn(true);
        when(dao.get(Student.class, 1L)).thenReturn(studentSubjectUtil.prepareStudentWithSchoolClass("1b"));
        when(dao.get(Subject.class, 1L)).thenReturn(studentSubjectUtil.prepareSubjectWithSchoolClass("1b"));

        lenient().when(resourceCreator.of(StudentSubjectValidators.EXCEPTION_MESSAGE_STUDENT_SUBJECT_ALREADY_EXIST, "null null", null))
                .thenReturn(StudentSubjectValidators.EXCEPTION_MESSAGE_STUDENT_SUBJECT_ALREADY_EXIST);

        // when
        Optional<ApiErrorDto> validationResult = existValidator.validate(dto);

        // then
        assertTrue(validationResult.isPresent());
        ApiErrorDto error = validationResult.get();
        assertEquals(error.getErrorThrownedBy(), StudentSubjectValidators.STUDENT_SUBJECT_ALREADY_EXIST_VALIDATOR_NAME);
        assertEquals(error.getField(), StudentSubjectRequestDto.ID_SUBJECT);
        assertEquals(error.getCause(), getErrorMessage(StudentSubjectValidators.EXCEPTION_MESSAGE_STUDENT_SUBJECT_ALREADY_EXIST));

    }

    @Test
    public void shouldReturnApiErrorWhenTryingToAssignStudentToSubjectFromDifferentSchoolClass() {
        // given
        StudentSubjectRequestDto dto = studentSubjectUtil.prepareStudentSubjectRequestDto(1L, 1L);

        when(dao.get(Student.class, 1L)).thenReturn(studentSubjectUtil.prepareStudentWithSchoolClass("1b"));
        when(dao.get(Subject.class, 1L)).thenReturn(studentSubjectUtil.prepareSubjectWithSchoolClass("2b"));
        lenient().when(resourceCreator.of(StudentSubjectValidators.EXCEPTION_MESSAGE_STUDENT_CANNOT_BE_ASSIGNED_TO_SUBJECT_FROM_DIFFERENT_CLASS, "null null", null))
                .thenReturn(StudentSubjectValidators.EXCEPTION_MESSAGE_STUDENT_CANNOT_BE_ASSIGNED_TO_SUBJECT_FROM_DIFFERENT_CLASS);

        // when
        Optional<ApiErrorDto> validationResult = validator.validate(dto);

        // then
        assertTrue(validationResult.isPresent());
        ApiErrorDto error = validationResult.get();
        assertEquals(error.getErrorThrownedBy(), StudentSubjectValidators.STUDENT_CANNOT_BE_ASSIGNED_TO_SUBJECT_FRON_DIFFERENT_CLASS_VALIDATOR_NAME);
        assertEquals(error.getField(), StudentSubjectRequestDto.ID_SUBJECT);
        assertEquals(error.getCause(), getErrorMessage(StudentSubjectValidators.EXCEPTION_MESSAGE_STUDENT_CANNOT_BE_ASSIGNED_TO_SUBJECT_FROM_DIFFERENT_CLASS));
    }

    @Test
    public void shouldNotReturnApiErrorWhenStudentSubjectNotExists() {
        // given
        StudentSubjectRequestDto dto = studentSubjectUtil.prepareStudentSubjectRequestDto(1L, 1L);

        when(dao.isStudentSubjectAlreadyExist(1L, 1L)).thenReturn(false);
        lenient().when(resourceCreator.of(StudentSubjectValidators.EXCEPTION_MESSAGE_STUDENT_SUBJECT_ALREADY_EXIST, "null null", null))
                .thenReturn(StudentSubjectValidators.EXCEPTION_MESSAGE_STUDENT_SUBJECT_ALREADY_EXIST);

        // when
        Optional<ApiErrorDto> validationResult = existValidator.validate(dto);

        // then
        assertFalse(validationResult.isPresent());
    }

    @Test
    public void shouldNotReturnApiErrorWhenTryingToAssignStudentToSubjectFromTheSameSchoolClass() {
        // given
        StudentSubjectRequestDto dto = studentSubjectUtil.prepareStudentSubjectRequestDto(1L, 1L);

        when(dao.get(Student.class, 1L)).thenReturn(studentSubjectUtil.prepareStudentWithSchoolClass("1b"));
        when(dao.get(Subject.class, 1L)).thenReturn(studentSubjectUtil.prepareSubjectWithSchoolClass("1b"));
        lenient().when(resourceCreator.of(StudentSubjectValidators.EXCEPTION_MESSAGE_STUDENT_CANNOT_BE_ASSIGNED_TO_SUBJECT_FROM_DIFFERENT_CLASS, "null null", null))
                .thenReturn(StudentSubjectValidators.EXCEPTION_MESSAGE_STUDENT_CANNOT_BE_ASSIGNED_TO_SUBJECT_FROM_DIFFERENT_CLASS);

        // when
        Optional<ApiErrorDto> validationResult = validator.validate(dto);

        // then
        assertFalse(validationResult.isPresent());
    }

}
