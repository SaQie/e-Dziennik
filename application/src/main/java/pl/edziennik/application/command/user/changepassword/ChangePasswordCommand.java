package pl.edziennik.application.command.user.changepassword;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.application.common.dispatcher.base.HandledBy;
import pl.edziennik.application.common.dispatcher.base.ValidatedBy;
import pl.edziennik.application.common.dispatcher.command.ICommand;
import pl.edziennik.common.valueobject.Password;
import pl.edziennik.common.valueobject.id.UserId;

/**
 *  A command used for changing a password to the user account
 */
@HandledBy(handler = ChangePasswordCommandHandler.class)
@ValidatedBy(validator = ChangePasswordCommandValidator.class)
public record ChangePasswordCommand(

        UserId userId,
        @NotNull Password oldPassword,
        @NotNull @Valid Password newPassword

) implements ICommand<OperationResult> {

    public static final String NEW_PASSWORD = "newPassword";
    public static final String OLD_PASSWORD = "oldPassword";
    public static final String USER_ID = "userId";

}
