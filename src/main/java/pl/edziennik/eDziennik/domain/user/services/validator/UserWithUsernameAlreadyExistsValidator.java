package pl.edziennik.eDziennik.domain.user.services.validator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.eDziennik.server.basics.dto.ApiErrorDto;
import pl.edziennik.eDziennik.server.exceptions.ExceptionType;
import pl.edziennik.eDziennik.server.basics.validator.ValidatorPriority;
import pl.edziennik.eDziennik.domain.user.dao.UserDao;
import pl.edziennik.eDziennik.domain.user.dto.UserRequestDto;
import pl.edziennik.eDziennik.server.utils.ResourceCreator;

import java.util.Optional;

/**
 * Validator for already existing usernames
 */
@Component
@AllArgsConstructor
class UserWithUsernameAlreadyExistsValidator implements UserValidators {

    private final UserDao dao;
    private final ResourceCreator resourceCreator;

    @Override
    public String getValidatorName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public Integer getValidationNumber() {
        return 1;
    }

    @Override
    public ValidatorPriority getValidationPriority() {
        return ValidatorPriority.HIGH;
    }

    @Override
    public Optional<ApiErrorDto> validate(UserRequestDto dto) {
        if (dao.isUserExistByUsername(dto.getUsername())) {

            String message = resourceCreator.of(EXCEPTION_MESSAGE_USER_ALREADY_EXISTS, dto.getUsername());

            ApiErrorDto apiErrorDto = ApiErrorDto.builder()
                    .field(UserRequestDto.USERNAME)
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
