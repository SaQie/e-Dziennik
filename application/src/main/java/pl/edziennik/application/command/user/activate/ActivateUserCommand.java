package pl.edziennik.application.command.user.activate;

import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.application.common.dispatcher.base.HandledBy;
import pl.edziennik.application.common.dispatcher.command.ICommand;
import pl.edziennik.common.valueobject.Token;

/**
 * A command used for activating the user account when the user was successfully registered
 */
@HandledBy(handler = ActivateUserCommandHandler.class)
public record ActivateUserCommand(
        Token token
) implements ICommand<OperationResult> {
}
