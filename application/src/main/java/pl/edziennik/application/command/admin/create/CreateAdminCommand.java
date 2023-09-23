package pl.edziennik.application.command.admin.create;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import pl.edziennik.application.common.dispatcher.Command;
import pl.edziennik.application.common.dispatcher.Handler;
import pl.edziennik.common.valueobject.id.AdminId;
import pl.edziennik.common.valueobject.vo.Email;
import pl.edziennik.common.valueobject.vo.Password;
import pl.edziennik.common.valueobject.vo.Pesel;
import pl.edziennik.common.valueobject.vo.Username;

/**
 * A command used for creating a new admin account
 * <br>
 * <b>Only one admin account can exist</b>
 */
@Handler(handler = CreateAdminCommandHandler.class, validator = CreateAdminCommandValidator.class)
public record CreateAdminCommand(

        @JsonIgnore AdminId adminId,

        @Schema(example = "ExampleUsername")
        @Valid @NotNull(message = "{username.empty}") Username username,
        @Schema(example = "Example@example.com")
        @Valid @NotNull(message = "{email.empty}") Email email,

        @Schema(example = "SuperSecretPassword123")
        @Valid @NotNull(message = "{password.empty}") Password password,

        @Schema(example = "22323213423")
        @Valid @NotNull(message = "{pesel.empty}") Pesel pesel

) implements Command {

    public static final String ADMIN_ID = "adminId";
    public static final String USERNAME = "username";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String PESEL = "pesel";


    @JsonCreator
    public CreateAdminCommand(Username username, Email email, Password password, Pesel pesel) {
        this(AdminId.create(), username, email, password, pesel);
    }
}
