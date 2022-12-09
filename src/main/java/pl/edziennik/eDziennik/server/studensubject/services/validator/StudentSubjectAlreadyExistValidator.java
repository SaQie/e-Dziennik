package pl.edziennik.eDziennik.server.studensubject.services.validator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.eDziennik.server.basics.ApiErrorsDto;
import pl.edziennik.eDziennik.server.basics.ExceptionType;
import pl.edziennik.eDziennik.server.basics.ValidatorPriority;
import pl.edziennik.eDziennik.server.studensubject.dao.StudentSubjectDao;
import pl.edziennik.eDziennik.server.studensubject.domain.StudentSubject;
import pl.edziennik.eDziennik.server.studensubject.domain.dto.request.StudentSubjectRequestDto;
import pl.edziennik.eDziennik.server.student.domain.Student;
import pl.edziennik.eDziennik.server.student.domain.dto.StudentRequestApiDto;
import pl.edziennik.eDziennik.server.subject.domain.Subject;
import pl.edziennik.eDziennik.server.utils.ResourceCreator;

import java.util.Optional;

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
            String studentName = student.getFirstName() + " " + student.getLastName();
            Subject subject = dao.get(Subject.class, dto.getIdSubject());
            String subjectName = subject.getName();
            String message = resourceCreator.of(EXCEPTION_MESSAGE_STUDENT_SUBJECT_ALREADY_EXIST, studentName, subjectName);
            ApiErrorsDto apiErrorsDto = new ApiErrorsDto(StudentSubjectRequestDto.ID_SUBJECT + " + " + StudentSubjectRequestDto.ID_STUDENT, message, false, getValidatorName(), ExceptionType.BUSINESS);
            return Optional.of(apiErrorsDto);
        }
        return Optional.empty();
    }
}
