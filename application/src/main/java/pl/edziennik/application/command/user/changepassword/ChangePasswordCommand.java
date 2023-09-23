package pl.edziennik.application.command.user.changepassword;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jdk.jfr.Experimental;
import pl.edziennik.application.common.dispatcher.*;
import pl.edziennik.common.valueobject.vo.Password;
import pl.edziennik.common.valueobject.id.UserId;

/**
 *  A command used for changing a password to the user account
 */
@Handler(handler = ChangePasswordCommandHandler.class,validator = ChangePasswordCommandValidator.class)
public record ChangePasswordCommand(

        @JsonIgnore UserId userId,

        @Schema(example = "oldPassword")
        @NotNull Password oldPassword,

        @Schema(example = "newPassword123")
        @NotNull @Valid Password newPassword

) implements Command {

    public static final String NEW_PASSWORD = "newPassword";
    public static final String OLD_PASSWORD = "oldPassword";
    public static final String USER_ID = "userId";

}
