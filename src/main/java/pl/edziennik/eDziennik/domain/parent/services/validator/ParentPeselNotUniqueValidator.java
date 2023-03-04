package pl.edziennik.eDziennik.domain.parent.services.validator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.eDziennik.domain.parent.dao.ParentDao;
import pl.edziennik.eDziennik.domain.parent.domain.dto.ParentRequestApiDto;
import pl.edziennik.eDziennik.server.basics.dto.ApiErrorDto;
import pl.edziennik.eDziennik.server.basics.validator.ValidatorPriority;
import pl.edziennik.eDziennik.server.exceptions.ExceptionType;
import pl.edziennik.eDziennik.server.utils.ResourceCreator;

import java.util.Optional;

/**
 * Validator that checks parent with given pesel already exists
 */
@Component
@AllArgsConstructor
class ParentPeselNotUniqueValidator implements ParentValidators {

    private final ParentDao dao;
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
    public Optional<ApiErrorDto> validate(ParentRequestApiDto dto) {
        if (dao.isParentExistsByPesel(dto.getPesel())) {

            String message = resourceCreator.of(EXCEPTION_MESSAGE_PARENT_PESEL_ALREADY_EXISTS, dto.getPesel());

            ApiErrorDto apiErrorDto = ApiErrorDto.builder()
                    .field(ParentRequestApiDto.PESEL)
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
