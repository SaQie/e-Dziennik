package pl.edziennik.eDziennik.domain.studensubject.services.validator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.eDziennik.domain.studensubject.dto.request.StudentSubjectRequestDto;
import pl.edziennik.eDziennik.server.basics.dto.ApiErrorsDto;
import pl.edziennik.eDziennik.server.exceptions.ExceptionType;
import pl.edziennik.eDziennik.server.basics.validator.ValidatorPriority;
import pl.edziennik.eDziennik.domain.studensubject.dao.StudentSubjectDao;
import pl.edziennik.eDziennik.domain.student.domain.Student;
import pl.edziennik.eDziennik.domain.subject.domain.Subject;
import pl.edziennik.eDziennik.server.utils.ResourceCreator;

import java.util.Optional;

/**
 * Validator that check, student has already assigned subject
 */
@Component
@AllArgsConstructor
class StudentSubjectAlreadyExistValidator implements StudentSubjectValidators{

    private final StudentSubjectDao dao;
    private final ResourceCreator resourceCreator;

    public static final Integer VALIDATOR_ID = 1;

    @Override
    public String getValidatorName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public Integer getValidationNumber() {
        return VALIDATOR_ID;
    }

    @Override
    public ValidatorPriority getValidationPriority() {
        return ValidatorPriority.HIGH;
    }

    @Override
    public Optional<ApiErrorsDto> validate(StudentSubjectRequestDto dto) {
        if (dao.isStudentSubjectAlreadyExist(dto.getIdStudent(), dto.getIdSubject())){

            Student student = dao.get(Student.class, dto.getIdStudent());
            Subject subject = dao.get(Subject.class, dto.getIdSubject());

            String studentName = student.getUser().getPersonInformation().getFirstName() + " " + student.getUser().getPersonInformation().getLastName();
            String subjectName = subject.getName();
            String message = resourceCreator.of(EXCEPTION_MESSAGE_STUDENT_SUBJECT_ALREADY_EXIST, studentName, subjectName);

            ApiErrorsDto apiErrorsDto = ApiErrorsDto.builder()
                    .field(StudentSubjectRequestDto.ID_SUBJECT)
                    .cause(message)
                    .thrownImmediately(false)
                    .errorThrownedBy(getValidatorName())
                    .exceptionType(ExceptionType.BUSINESS)
                    .build();

            return Optional.of(apiErrorsDto);
        }
        return Optional.empty();
    }
}
