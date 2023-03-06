package pl.edziennik.eDziennik.domain.user.services.validator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.eDziennik.server.basics.dto.ApiErrorDto;
import pl.edziennik.eDziennik.server.exceptions.ExceptionType;
import pl.edziennik.eDziennik.server.basics.validator.ValidatePurpose;
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
    public String getValidatorId() {
        return USER_WITH_USERNAME_ALREADY_EXIST_VALIDATOR_NAME;
    }

    @Override
    public Optional<ApiErrorDto> validate(UserRequestDto dto) {
        if (dao.isUserExistByUsername(dto.getUsername())) {

            String message = resourceCreator.of(EXCEPTION_MESSAGE_USER_ALREADY_EXISTS, dto.getUsername());

            ApiErrorDto apiErrorDto = ApiErrorDto.builder()
                    .field(UserRequestDto.USERNAME)
                    .cause(message)
                    .thrownImmediately(false)
                    .errorThrownedBy(getValidatorId())
                    .exceptionType(ExceptionType.BUSINESS)
                    .build();

            return Optional.of(apiErrorDto);

        }
        return Optional.empty();
    }
}
