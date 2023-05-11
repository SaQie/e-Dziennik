package pl.edziennik.application.command.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.application.common.dispatcher.base.HandledBy;
import pl.edziennik.application.common.dispatcher.base.ValidatedBy;
import pl.edziennik.application.common.dispatcher.command.ICommand;
import pl.edziennik.common.valueobject.Password;
import pl.edziennik.common.valueobject.Username;

@HandledBy(handler = CreateAdminCommandHandler.class)
@ValidatedBy(validator = CreateAdminCommandValidator.class)
public record CreateAdminCommand(

        @NotEmpty(message = "{username.empty}")
        @Size(min = 4, message = "{username.size}")
        Username username,

        @Email(message = "{email.is.not.valid}")
        @NotEmpty(message = "{email.empty}")
        pl.edziennik.common.valueobject.Email email,

        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        @NotEmpty(message = "{password.empty}")
        @Size(min = 5, message = "{password.size}")
        Password password

) implements ICommand<OperationResult> {

    public static final String USERNAME = "username";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";

}
