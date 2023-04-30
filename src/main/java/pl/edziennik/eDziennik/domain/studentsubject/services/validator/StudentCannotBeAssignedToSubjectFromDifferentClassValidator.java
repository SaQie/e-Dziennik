package pl.edziennik.eDziennik.domain.studentsubject.services.validator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.eDziennik.domain.student.domain.Student;
import pl.edziennik.eDziennik.domain.student.repository.StudentRepository;
import pl.edziennik.eDziennik.domain.studentsubject.dto.request.StudentSubjectRequestDto;
import pl.edziennik.eDziennik.domain.studentsubject.repository.StudentSubjectRepository;
import pl.edziennik.eDziennik.domain.subject.domain.Subject;
import pl.edziennik.eDziennik.domain.subject.repository.SubjectRepository;
import pl.edziennik.eDziennik.server.basics.dto.ApiValidationResult;
import pl.edziennik.eDziennik.server.basics.service.BaseService;
import pl.edziennik.eDziennik.server.exceptions.ExceptionType;
import pl.edziennik.eDziennik.server.utils.ResourceCreator;

import java.util.Optional;

/**
 * Validator that check the selected subject is from the same school class as student
 */
@Component
@AllArgsConstructor
class StudentCannotBeAssignedToSubjectFromDifferentClassValidator extends BaseService implements StudentSubjectValidators {

    private final StudentSubjectRepository studentSubjectRepository;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;


    private final ResourceCreator resourceCreator;


    @Override
    public String getValidatorId() {
        return STUDENT_CANNOT_BE_ASSIGNED_TO_SUBJECT_FRON_DIFFERENT_CLASS_VALIDATOR_NAME;
    }

    @Override
    public Optional<ApiValidationResult> validate(StudentSubjectRequestDto dto) {
        Student student = studentRepository.findById(dto.studentId())
                .orElseThrow(notFoundException(dto.studentId(), Student.class));
        Subject subject = subjectRepository.findById(dto.subjectId())
                .orElseThrow(notFoundException(dto.subjectId(), Subject.class));

        if (!student.getSchoolClass().getSchoolClassId().equals(subject.getSchoolClass().getSchoolClassId())) {
            String studentName = student.getPersonInformation().fullName();
            String subjectName = subject.getName();

            String message = resourceCreator.of(EXCEPTION_MESSAGE_STUDENT_CANNOT_BE_ASSIGNED_TO_SUBJECT_FROM_DIFFERENT_CLASS, studentName, subjectName);

            ApiValidationResult apiValidationResult = ApiValidationResult.builder()
                    .field(StudentSubjectRequestDto.ID_SUBJECT)
                    .cause(message)
                    .thrownImmediately(false)
                    .errorThrownedBy(getValidatorId())
                    .exceptionType(ExceptionType.BUSINESS)
                    .build();

            return Optional.of(apiValidationResult);
        }
        return Optional.empty();
    }
}
