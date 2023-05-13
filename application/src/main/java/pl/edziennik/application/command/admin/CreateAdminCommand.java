package pl.edziennik.application.command.admin;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.application.common.dispatcher.base.HandledBy;
import pl.edziennik.application.common.dispatcher.base.ValidatedBy;
import pl.edziennik.application.common.dispatcher.command.ICommand;
import pl.edziennik.common.valueobject.Password;
import pl.edziennik.common.valueobject.Username;

@HandledBy(handler = CreateAdminCommandHandler.class)
@ValidatedBy(validator = CreateAdminCommandValidator.class)
public record CreateAdminCommand(

        @Valid @NotNull(message = "{username.empty}") Username username,
        @Valid @NotNull(message = "{email.empty}") pl.edziennik.common.valueobject.Email email,
        @Valid @NotNull(message = "{password.empty}") Password password

) implements ICommand<OperationResult> {

    public static final String USERNAME = "username";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";

}
