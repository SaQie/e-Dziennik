package pl.edziennik.eDziennik.domain.admin.services.validator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.eDziennik.domain.admin.dao.AdminDao;
import pl.edziennik.eDziennik.domain.admin.dto.AdminRequestApiDto;
import pl.edziennik.eDziennik.server.basics.dto.ApiErrorDto;
import pl.edziennik.eDziennik.server.exceptions.ExceptionType;
import pl.edziennik.eDziennik.server.basics.validator.ValidatorPriority;
import pl.edziennik.eDziennik.server.utils.ResourceCreator;

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
    public Optional<ApiErrorDto> validate(AdminRequestApiDto dto) {
        if (!dao.findAll().isEmpty()){

            String message = resourceCreator.of(EXCEPTION_MESSAGE_ADMIN_ALREADY_EXIST);

            ApiErrorDto apiErrorDto = ApiErrorDto.builder()
                    .cause(message)
                    .thrownImmediately(true)
                    .errorThrownedBy(getValidatorName())
                    .exceptionType(ExceptionType.BUSINESS)
                    .build();

            return Optional.of(apiErrorDto);
        }
        return Optional.empty();
    }
}
