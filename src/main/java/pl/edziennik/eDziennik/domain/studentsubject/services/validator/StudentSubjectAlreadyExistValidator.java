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
 * Validator that check, student has already assigned subject
 */
@Component
@AllArgsConstructor
class StudentSubjectAlreadyExistValidator extends BaseService implements StudentSubjectValidators {

    private final StudentSubjectRepository repository;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;


    private final ResourceCreator resourceCreator;

    @Override
    public String getValidatorId() {
        return STUDENT_SUBJECT_ALREADY_EXIST_VALIDATOR_NAME;
    }

    @Override
    public Optional<ApiValidationResult> validate(StudentSubjectRequestDto dto) {
        if (repository.existsByStudentIdAndSubjectId(dto.idStudent(), dto.idSubject())) {

            Student student = studentRepository.findById(dto.idStudent())
                    .orElseThrow(notFoundException(dto.idStudent(), Student.class));
            Subject subject = subjectRepository.findById(dto.idSubject())
                    .orElseThrow(notFoundException(dto.idSubject(), Subject.class));

            String studentName = student.getPersonInformation().getFullName();
            String subjectName = subject.getName();

            String message = resourceCreator.of(EXCEPTION_MESSAGE_STUDENT_SUBJECT_ALREADY_EXIST, studentName, subjectName);

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
