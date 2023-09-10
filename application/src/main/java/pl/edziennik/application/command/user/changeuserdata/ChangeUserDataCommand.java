package pl.edziennik.application.command.user.changeuserdata;

import jakarta.validation.Valid;
import pl.edziennik.application.common.dispatcher.*;
import pl.edziennik.common.valueobject.vo.Email;
import pl.edziennik.common.valueobject.vo.Username;
import pl.edziennik.common.valueobject.id.UserId;

/**
 * A Command used for updating basic user data (username, or/and email)
 */
@Handler(handler = ChangeUserDataCommandHandler.class, validator = ChangeUserDataCommandValidator.class)
public record ChangeUserDataCommand(

        UserId userId,
        @Valid Username username,
        @Valid Email email

) implements Command {

    public static final String USER_ID = "userId";
    public static final String USERNAME = "username";
    public static final String EMAIL = "email";

}
