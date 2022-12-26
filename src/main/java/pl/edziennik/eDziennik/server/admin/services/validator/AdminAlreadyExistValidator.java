package pl.edziennik.eDziennik.server.admin.services.validator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.eDziennik.server.admin.dao.AdminDao;
import pl.edziennik.eDziennik.server.admin.domain.dto.AdminRequestApiDto;
import pl.edziennik.eDziennik.server.basics.ApiErrorsDto;
import pl.edziennik.eDziennik.server.basics.ExceptionType;
import pl.edziennik.eDziennik.server.basics.ValidatorPriority;
import pl.edziennik.eDziennik.server.student.dao.StudentDao;
import pl.edziennik.eDziennik.server.student.domain.Student;
import pl.edziennik.eDziennik.server.student.domain.dto.StudentRequestApiDto;
import pl.edziennik.eDziennik.server.utils.ResourceCreator;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
class AdminAlreadyExistValidator implements AdminValidators{

    private final ResourceCreator resourceCreator;
    private final AdminDao dao;

    private static final Integer VALIDATOR_ID = 1;

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
    public Optional<ApiErrorsDto> validate(AdminRequestApiDto dto) {
        if (!dao.findAll().isEmpty()){

            String message = resourceCreator.of(EXCEPTION_MESSAGE_ADMIN_ALREADY_EXIST);

            ApiErrorsDto apiErrorsDto = ApiErrorsDto.builder()
                    .cause(message)
                    .thrownImmediately(true)
                    .errorThrownedBy(getValidatorName())
                    .exceptionType(ExceptionType.BUSINESS)
                    .build();

            return Optional.of(apiErrorsDto);
        }
        return Optional.empty();
    }
}
