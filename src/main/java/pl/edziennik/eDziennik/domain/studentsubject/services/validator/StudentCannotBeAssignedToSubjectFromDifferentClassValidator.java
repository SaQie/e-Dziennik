package pl.edziennik.eDziennik.domain.studentsubject.services.validator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.eDziennik.domain.student.domain.Student;
import pl.edziennik.eDziennik.domain.student.repository.StudentRepository;
import pl.edziennik.eDziennik.domain.studentsubject.dto.request.StudentSubjectRequestDto;
import pl.edziennik.eDziennik.domain.studentsubject.repository.StudentSubjectRepository;
import pl.edziennik.eDziennik.domain.subject.domain.Subject;
import pl.edziennik.eDziennik.domain.subject.repository.SubjectRepository;
import pl.edziennik.eDziennik.server.basics.dto.ApiErrorDto;
import pl.edziennik.eDziennik.server.basics.service.BaseService;
import pl.edziennik.eDziennik.server.exceptions.ExceptionType;
import pl.edziennik.eDziennik.server.utils.PersonUtils;
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
    public Optional<ApiErrorDto> validate(StudentSubjectRequestDto dto) {
        Student student = studentRepository.findById(dto.getIdStudent())
                .orElseThrow(notFoundException(dto.getIdStudent(), Student.class));
        Subject subject = subjectRepository.findById(dto.getIdSubject())
                .orElseThrow(notFoundException(dto.getIdSubject(), Subject.class));

        if (!student.getSchoolClass().equals(subject.getSchoolClass())) {
            String studentName = student.getPersonInformation().getFullName();
            String subjectName = subject.getName();

            String message = resourceCreator.of(EXCEPTION_MESSAGE_STUDENT_CANNOT_BE_ASSIGNED_TO_SUBJECT_FROM_DIFFERENT_CLASS, studentName, subjectName);

            ApiErrorDto apiErrorDto = ApiErrorDto.builder()
                    .field(StudentSubjectRequestDto.ID_SUBJECT)
                    .cause(message)
                    .thrownImmediately(false)
                    .errorThrownedBy(getValidatorId())
                    .exceptionType(ExceptionType.BUSINESS)
                    .build();

            return Optional.of(apiErrorDto);
        }
        return Optional.empty();
    }
}
