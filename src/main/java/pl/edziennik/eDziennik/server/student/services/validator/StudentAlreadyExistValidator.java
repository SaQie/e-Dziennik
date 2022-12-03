package pl.edziennik.eDziennik.server.student.services.validator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.eDziennik.server.basics.ApiErrorsDto;
import pl.edziennik.eDziennik.server.basics.ValidatorPriority;
import pl.edziennik.eDziennik.server.student.dao.StudentDao;
import pl.edziennik.eDziennik.server.student.domain.Student;
import pl.edziennik.eDziennik.server.student.domain.dto.StudentRequestApiDto;
import pl.edziennik.eDziennik.server.teacher.domain.Teacher;
import pl.edziennik.eDziennik.server.teacher.domain.dto.TeacherRequestApiDto;
import pl.edziennik.eDziennik.server.utils.ResourceCreator;

import java.util.Optional;

@Component
@AllArgsConstructor
public class StudentAlreadyExistValidator implements StudentValidators{

    private final ResourceCreator resourceCreator;
    private final StudentDao dao;

    private static final Integer VALIDATOR_ID = 1;

    @Override
    public String getValidatorName() {
        return this.getClass().getName();
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
    public Optional<ApiErrorsDto> validate(StudentRequestApiDto requestApiDto) {
        if(dao.isStudentExist(requestApiDto.getUsername())){
            String message = resourceCreator.of(EXCEPTION_MESSAGE_STUDENT_ALREADY_EXIST, Student.class.getSimpleName(), requestApiDto.getUsername());
            ApiErrorsDto apiErrorsDto = new ApiErrorsDto(StudentRequestApiDto.USERNAME, message, false, getValidatorName());
            return Optional.of(apiErrorsDto);
        }
        return Optional.empty();
    }
}
