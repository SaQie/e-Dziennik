package pl.edziennik.eDziennik.domain.studentsubject.services.validator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.eDziennik.domain.studentsubject.dto.request.StudentSubjectRequestDto;
import pl.edziennik.eDziennik.server.basics.dto.ApiErrorDto;
import pl.edziennik.eDziennik.server.exceptions.ExceptionType;
import pl.edziennik.eDziennik.server.basics.validator.ValidatePurpose;
import pl.edziennik.eDziennik.domain.studentsubject.dao.StudentSubjectDao;
import pl.edziennik.eDziennik.domain.student.domain.Student;
import pl.edziennik.eDziennik.domain.subject.domain.Subject;
import pl.edziennik.eDziennik.server.utils.ResourceCreator;

import java.util.Optional;

/**
 * Validator that check, student has already assigned subject
 */
@Component
@AllArgsConstructor
class StudentSubjectAlreadyExistValidator implements StudentSubjectValidators {

    private final StudentSubjectDao dao;
    private final ResourceCreator resourceCreator;

    @Override
    public String getValidatorId() {
        return STUDENT_SUBJECT_ALREADY_EXIST_VALIDATOR_NAME;
    }

    @Override
    public Optional<ApiErrorDto> validate(StudentSubjectRequestDto dto) {
        if (dao.isStudentSubjectAlreadyExist(dto.getIdStudent(), dto.getIdSubject())) {

            Student student = dao.get(Student.class, dto.getIdStudent());
            Subject subject = dao.get(Subject.class, dto.getIdSubject());

            String studentName = student.getPersonInformation().getFirstName() + " " + student.getPersonInformation().getLastName();
            String subjectName = subject.getName();
            String message = resourceCreator.of(EXCEPTION_MESSAGE_STUDENT_SUBJECT_ALREADY_EXIST, studentName, subjectName);

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
