package pl.edziennik.eDziennik.server.teacher.services.validator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.eDziennik.server.basics.ApiErrorsDto;
import pl.edziennik.eDziennik.server.basics.ValidatorPriority;
import pl.edziennik.eDziennik.server.teacher.dao.TeacherDao;
import pl.edziennik.eDziennik.server.teacher.domain.Teacher;
import pl.edziennik.eDziennik.server.teacher.domain.dto.TeacherRequestApiDto;
import pl.edziennik.eDziennik.server.utils.ResourceCreator;

import java.util.Optional;

@Component
@AllArgsConstructor
public class TeacherAlreadyExistValidator implements TeacherValidators {

    private final TeacherDao dao;
    private final ResourceCreator resourceCreator;

    public static final Integer VALIDATOR_ID = 1;
    public static final String ERROR_MESSAGE_TEACHER_ALREADY_EXIST = "api.already.exist";

    @Override
    public String getValidatorName(){
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
    public Optional<ApiErrorsDto> validate(TeacherRequestApiDto requestApiDto) {
        if(dao.isTeacherExist(requestApiDto.getUsername())){
            String message = resourceCreator.of(ERROR_MESSAGE_TEACHER_ALREADY_EXIST, Teacher.class.getSimpleName(), requestApiDto.getUsername());
            ApiErrorsDto apiErrorsDto = new ApiErrorsDto(TeacherRequestApiDto.USERNAME, message, false, getValidatorName());
            return Optional.of(apiErrorsDto);
        }
        return Optional.empty();
    }
}
