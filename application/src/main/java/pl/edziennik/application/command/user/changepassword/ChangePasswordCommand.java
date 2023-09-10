package pl.edziennik.application.command.user.changepassword;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import pl.edziennik.application.common.dispatcher.*;
import pl.edziennik.common.valueobject.vo.Password;
import pl.edziennik.common.valueobject.id.UserId;

/**
 *  A command used for changing a password to the user account
 */
@Handler(handler = ChangePasswordCommandHandler.class,validator = ChangePasswordCommandValidator.class)
public record ChangePasswordCommand(

        UserId userId,
        @NotNull Password oldPassword,
        @NotNull @Valid Password newPassword

) implements Command {

    public static final String NEW_PASSWORD = "newPassword";
    public static final String OLD_PASSWORD = "oldPassword";
    public static final String USER_ID = "userId";

}
