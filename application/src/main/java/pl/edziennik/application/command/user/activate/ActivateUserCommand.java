package pl.edziennik.application.command.user.activate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import pl.edziennik.application.common.dispatcher.*;
import pl.edziennik.common.valueobject.vo.Token;

/**
 * A command used for activating the user account when the user was successfully registered
 */
@Handler(handler = ActivateUserCommandHandler.class)
public record ActivateUserCommand(

        @JsonIgnore Token token

) implements Command {

    public static final String TOKEN = "token";

}
