package pl.edziennik.application.command.parent.create;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import pl.edziennik.application.common.dispatcher.Command;
import pl.edziennik.application.common.dispatcher.Handler;
import pl.edziennik.common.valueobject.id.ParentId;
import pl.edziennik.common.valueobject.id.StudentId;
import pl.edziennik.common.valueobject.vo.*;

/**
 * A command used for creating a new parent command
 */
@Handler(handler = CreateParentCommandHandler.class, validator = CreateParentCommandValidator.class)
public record CreateParentCommand(

        @JsonIgnore ParentId parentId,

        @Schema(example = "SuperSecretPassword123")
        @Valid @NotNull(message = "{password.empty}") Password password,

        @Schema(example = "SuperUser")
        @Valid @NotNull(message = "{username.empty}") Username username,

        @Schema(example = "Kamil")
        @Valid @NotNull(message = "{firstName.empty}") FirstName firstName,

        @Schema(example = "Nowak")
        @Valid @NotNull(message = "{lastName.empty}") LastName lastName,

        @Schema(example = "Mostowa 2/2")
        @Valid @NotNull(message = "{address.empty}") Address address,

        @Schema(example = "22-200")
        @Valid @NotNull(message = "{postalCode.empty}") PostalCode postalCode,

        @Schema(example = "Warszawa")
        @Valid @NotNull(message = "{city.empty}") City city,

        @Schema(example = "22323213423")
        @Valid @NotNull(message = "{pesel.invalid}") Pesel pesel,

        @Schema(example = "test@example.com")
        @Valid @NotNull(message = "{email.empty}") Email email,

        @Schema(example = "601652322")
        @Valid @NotNull(message = "{phone.invalid}") PhoneNumber phoneNumber,
        StudentId studentId

) implements Command {

    public static final String PASSWORD = "password";
    public static final String USERNAME = "username";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String ADDRESS = "address";
    public static final String POSTAL_CODE = "postalCode";
    public static final String CITY = "city";
    public static final String PESEL = "pesel";
    public static final String EMAIL = "email";
    public static final String PHONE_NUMBER = "phoneNumber";
    public static final String STUDENT_ID = "studentId";


    @JsonCreator
    public CreateParentCommand(Password password, Username username, FirstName firstName, LastName lastName,
                               Address address, PostalCode postalCode, City city, Pesel pesel, Email email,
                               PhoneNumber phoneNumber, StudentId studentId) {
        this(ParentId.create(), password, username, firstName, lastName, address, postalCode, city, pesel, email, phoneNumber, studentId);
    }
}
