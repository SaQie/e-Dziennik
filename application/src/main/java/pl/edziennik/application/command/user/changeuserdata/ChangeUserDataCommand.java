package pl.edziennik.application.command.user.changeuserdata;

import jakarta.validation.Valid;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.application.common.dispatcher.HandledBy;
import pl.edziennik.application.common.dispatcher.ValidatedBy;
import pl.edziennik.application.common.dispatcher.ICommand;
import pl.edziennik.common.valueobject.vo.Email;
import pl.edziennik.common.valueobject.vo.Username;
import pl.edziennik.common.valueobject.id.UserId;

/**
 * A Command used for updating basic user data (username, or/and email)
 */
@HandledBy(handler = ChangeUserDataCommandHandler.class)
@ValidatedBy(validator = ChangeUserDataCommandValidator.class)
public record ChangeUserDataCommand(

        UserId userId,
        @Valid Username username,
        @Valid Email email

) implements ICommand<OperationResult> {

    public static final String USER_ID = "userId";
    public static final String USERNAME = "username";
    public static final String EMAIL = "email";

}
