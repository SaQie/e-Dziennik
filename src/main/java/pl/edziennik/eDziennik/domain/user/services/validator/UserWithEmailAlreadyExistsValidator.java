package pl.edziennik.eDziennik.domain.user.services.validator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.eDziennik.domain.user.dto.UserRequestDto;
import pl.edziennik.eDziennik.domain.user.repository.UserRepository;
import pl.edziennik.eDziennik.server.basic.dto.ApiValidationResult;
import pl.edziennik.eDziennik.server.exception.ExceptionType;
import pl.edziennik.eDziennik.server.utils.ResourceCreator;

import java.util.Optional;

/**
 * Validator for already existing user email
 */
@Component
@AllArgsConstructor
class UserWithEmailAlreadyExistsValidator implements UserValidators {

    private final UserRepository repository;
    private final ResourceCreator resourceCreator;

    @Override
    public String getValidatorId() {
        return USER_WITH_EMAIL_ALREADY_EXIST_VALIDATOR_NAME;
    }

    @Override
    public Optional<ApiValidationResult> validate(UserRequestDto dto) {
        if (repository.existsByEmail(dto.email())) {

            String message = resourceCreator.of(EXCEPTION_MESSAGE_USER_ALREADY_EXISTS_BY_EMAIL, dto.email());

            ApiValidationResult apiValidationResult = ApiValidationResult.builder()
                    .field(UserRequestDto.EMAIL)
                    .cause(message)
                    .thrownImmediately(false)
                    .errorThrownedBy(getValidatorId())
                    .exceptionType(ExceptionType.BUSINESS)
                    .build();

            return Optional.of(apiValidationResult);

        }
        return Optional.empty();
    }
}
