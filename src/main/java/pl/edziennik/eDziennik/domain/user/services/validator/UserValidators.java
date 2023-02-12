package pl.edziennik.eDziennik.domain.user.services.validator;

import pl.edziennik.eDziennik.server.basics.validator.AbstractValidator;
import pl.edziennik.eDziennik.domain.user.dto.UserRequestDto;

public interface UserValidators extends AbstractValidator<UserRequestDto> {

    String USER_WITH_EMAIL_ALREADY_EXIST_VALIDATOR_NAME = UserWithEmailAlreadyExistsValidator.class.getSimpleName();
    String USER_WITH_USERNAME_ALREADY_EXIST_VALIDATOR_NAME = UserWithUsernameAlreadyExistsValidator.class.getSimpleName();

    String EXCEPTION_MESSAGE_USER_ALREADY_EXISTS = "user.already.exists";
    String EXCEPTION_MESSAGE_USER_ALREADY_EXISTS_BY_EMAIL = "user.already.exists.by.email";

}
