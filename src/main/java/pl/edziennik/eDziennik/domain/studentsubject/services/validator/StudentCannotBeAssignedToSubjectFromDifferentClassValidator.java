package pl.edziennik.eDziennik.domain.studentsubject.services.validator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.eDziennik.domain.studentsubject.dto.request.StudentSubjectRequestDto;
import pl.edziennik.eDziennik.server.basics.dto.ApiErrorDto;
import pl.edziennik.eDziennik.server.exceptions.ExceptionType;
import pl.edziennik.eDziennik.server.basics.validator.ValidatorPriority;
import pl.edziennik.eDziennik.domain.studentsubject.dao.StudentSubjectDao;
import pl.edziennik.eDziennik.domain.student.domain.Student;
import pl.edziennik.eDziennik.domain.subject.domain.Subject;
import pl.edziennik.eDziennik.server.utils.ResourceCreator;

import java.util.Optional;

/**
 * Validator that check the selected subject is from the same school class as student
 */
@Component
@AllArgsConstructor
class StudentCannotBeAssignedToSubjectFromDifferentClassValidator implements StudentSubjectValidators{

    private final StudentSubjectDao dao;
    private final ResourceCreator resourceCreator;

    public static final Integer VALIDATOR_ID = 2;


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
    public Optional<ApiErrorDto> validate(StudentSubjectRequestDto dto) {
        Student student = dao.get(Student.class, dto.getIdStudent());
        Subject subject = dao.get(Subject.class, dto.getIdSubject());

        if (!student.getSchoolClass().equals(subject.getSchoolClass())){
            String studentName = student.getPersonInformation().getFirstName() + " " + student.getPersonInformation().getLastName();
            String subjectName = subject.getName();

            String message = resourceCreator.of(EXCEPTION_MESSAGE_STUDENT_CANNOT_BE_ASSIGNED_TO_SUBJECT_FROM_DIFFERENT_CLASS, studentName, subjectName);

            ApiErrorDto apiErrorDto = ApiErrorDto.builder()
                    .field(StudentSubjectRequestDto.ID_SUBJECT)
                    .cause(message)
                    .thrownImmediately(false)
                    .errorThrownedBy(getValidatorName())
                    .exceptionType(ExceptionType.BUSINESS)
                    .build();

            return Optional.of(apiErrorDto);
        }
        return Optional.empty();
    }
}