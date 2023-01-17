package pl.edziennik.eDziennik.server.user.services.validator;

import pl.edziennik.eDziennik.server.basics.AbstractValidator;
import pl.edziennik.eDziennik.server.user.domain.User;
import pl.edziennik.eDziennik.server.user.domain.dto.UserRequestDto;

public interface UserValidators extends AbstractValidator<UserRequestDto> {

    String EXCEPTION_MESSAGE_USER_ALREADY_EXISTS = "user.already.exists";
    String EXCEPTION_MESSAGE_USER_ALREADY_EXISTS_BY_EMAIL = "user.already.exists.by.email";

}
