package pl.edziennik.eDziennik.domain.studentsubject.services.validator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.edziennik.eDziennik.BaseUnitTest;
import pl.edziennik.eDziennik.domain.student.domain.wrapper.StudentId;
import pl.edziennik.eDziennik.domain.student.repository.StudentRepository;
import pl.edziennik.eDziennik.domain.studentsubject.dto.request.StudentSubjectRequestDto;
import pl.edziennik.eDziennik.domain.studentsubject.repository.StudentSubjectRepository;
import pl.edziennik.eDziennik.domain.subject.domain.wrapper.SubjectId;
import pl.edziennik.eDziennik.domain.subject.repository.SubjectRepository;
import pl.edziennik.eDziennik.server.basics.dto.ApiValidationResult;
import pl.edziennik.eDziennik.server.utils.ResourceCreator;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StudentSubjectValidatorUnitTest extends BaseUnitTest {

    @InjectMocks
    private StudentCannotBeAssignedToSubjectFromDifferentClassValidator validator;

    @InjectMocks
    private StudentSubjectAlreadyExistValidator existValidator;

    @Mock
    private StudentSubjectRepository repository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private SubjectRepository subjectRepository;

    @Mock
    private ResourceCreator resourceCreator;

    @Test
    public void shouldReturnApiErrorWhenStudentSubjectAlreadyExists() {
        // given
        SubjectId subjectId = SubjectId.wrap(1L);
        StudentId studentId = StudentId.wrap(1L);
        StudentSubjectRequestDto dto = studentSubjectUtil.prepareStudentSubjectRequestDto(subjectId, studentId);

        when(repository.existsByStudentIdAndSubjectId(studentId, subjectId)).thenReturn(true);
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(studentSubjectUtil.prepareStudentWithSchoolClass(1L,"1b")));
        when(subjectRepository.findById(subjectId)).thenReturn(Optional.of(studentSubjectUtil.prepareSubjectWithSchoolClass(1L,"1b")));

        lenient().when(resourceCreator.of(StudentSubjectValidators.EXCEPTION_MESSAGE_STUDENT_SUBJECT_ALREADY_EXIST, null, null))
                .thenReturn(StudentSubjectValidators.EXCEPTION_MESSAGE_STUDENT_SUBJECT_ALREADY_EXIST);

        // when
        Optional<ApiValidationResult> validationResult = existValidator.validate(dto);

        // then
        assertTrue(validationResult.isPresent());
        ApiValidationResult error = validationResult.get();
        assertEquals(error.getErrorThrownedBy(), StudentSubjectValidators.STUDENT_SUBJECT_ALREADY_EXIST_VALIDATOR_NAME);
        assertEquals(error.getField(), StudentSubjectRequestDto.ID_SUBJECT);
        assertEquals(error.getCause(), getErrorMessage(StudentSubjectValidators.EXCEPTION_MESSAGE_STUDENT_SUBJECT_ALREADY_EXIST));

    }

    @Test
    public void shouldReturnApiErrorWhenTryingToAssignStudentToSubjectFromDifferentSchoolClass() {
        // given
        SubjectId subjectId = SubjectId.wrap(1L);
        StudentId studentId = StudentId.wrap(1L);
        StudentSubjectRequestDto dto = studentSubjectUtil.prepareStudentSubjectRequestDto(subjectId, studentId);

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(studentSubjectUtil.prepareStudentWithSchoolClass(1L,"1b")));
        when(subjectRepository.findById(subjectId)).thenReturn(Optional.of(studentSubjectUtil.prepareSubjectWithSchoolClass(2L,"2b")));
        lenient().when(resourceCreator.of(StudentSubjectValidators.EXCEPTION_MESSAGE_STUDENT_CANNOT_BE_ASSIGNED_TO_SUBJECT_FROM_DIFFERENT_CLASS, null, null))
                .thenReturn(StudentSubjectValidators.EXCEPTION_MESSAGE_STUDENT_CANNOT_BE_ASSIGNED_TO_SUBJECT_FROM_DIFFERENT_CLASS);

        // when
        Optional<ApiValidationResult> validationResult = validator.validate(dto);

        // then
        assertTrue(validationResult.isPresent());
        ApiValidationResult error = validationResult.get();
        assertEquals(error.getErrorThrownedBy(), StudentSubjectValidators.STUDENT_CANNOT_BE_ASSIGNED_TO_SUBJECT_FRON_DIFFERENT_CLASS_VALIDATOR_NAME);
        assertEquals(error.getField(), StudentSubjectRequestDto.ID_SUBJECT);
        assertEquals(error.getCause(), getErrorMessage(StudentSubjectValidators.EXCEPTION_MESSAGE_STUDENT_CANNOT_BE_ASSIGNED_TO_SUBJECT_FROM_DIFFERENT_CLASS));
    }

    @Test
    public void shouldNotReturnApiErrorWhenStudentSubjectNotExists() {
        // given
        SubjectId subjectId = SubjectId.wrap(1L);
        StudentId studentId = StudentId.wrap(1L);
        StudentSubjectRequestDto dto = studentSubjectUtil.prepareStudentSubjectRequestDto(subjectId, studentId);

        when(repository.existsByStudentIdAndSubjectId(studentId, subjectId)).thenReturn(false);
        lenient().when(resourceCreator.of(StudentSubjectValidators.EXCEPTION_MESSAGE_STUDENT_SUBJECT_ALREADY_EXIST, "null null", null))
                .thenReturn(StudentSubjectValidators.EXCEPTION_MESSAGE_STUDENT_SUBJECT_ALREADY_EXIST);

        // when
        Optional<ApiValidationResult> validationResult = existValidator.validate(dto);

        // then
        assertFalse(validationResult.isPresent());
    }

    @Test
    public void shouldNotReturnApiErrorWhenTryingToAssignStudentToSubjectFromTheSameSchoolClass() {
        // given
        SubjectId subjectId = SubjectId.wrap(1L);
        StudentId studentId = StudentId.wrap(1L);
        StudentSubjectRequestDto dto = studentSubjectUtil.prepareStudentSubjectRequestDto(subjectId, studentId);

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(studentSubjectUtil.prepareStudentWithSchoolClass(1L,"1b")));
        when(subjectRepository.findById(subjectId)).thenReturn(Optional.of(studentSubjectUtil.prepareSubjectWithSchoolClass(1L,"1b")));
        lenient().when(resourceCreator.of(StudentSubjectValidators.EXCEPTION_MESSAGE_STUDENT_CANNOT_BE_ASSIGNED_TO_SUBJECT_FROM_DIFFERENT_CLASS, "null null", null))
                .thenReturn(StudentSubjectValidators.EXCEPTION_MESSAGE_STUDENT_CANNOT_BE_ASSIGNED_TO_SUBJECT_FROM_DIFFERENT_CLASS);

        // when
        Optional<ApiValidationResult> validationResult = validator.validate(dto);

        // then
        assertFalse(validationResult.isPresent());
    }

}
