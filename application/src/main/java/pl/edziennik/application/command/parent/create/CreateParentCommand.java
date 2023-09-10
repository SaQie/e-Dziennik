package pl.edziennik.application.command.parent.create;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import pl.edziennik.application.common.dispatcher.Command;
import pl.edziennik.application.common.dispatcher.Handler;
import pl.edziennik.common.valueobject.id.StudentId;
import pl.edziennik.common.valueobject.vo.*;

/**
 * A command used for creating a new parent command
 */
@Handler(handler = CreateParentCommandHandler.class, validator = CreateParentCommandValidator.class)
public record CreateParentCommand(

        @Valid @NotNull(message = "{password.empty}") Password password,
        @Valid @NotNull(message = "{username.empty}") Username username,
        @Valid @NotNull(message = "{firstName.empty}") FirstName firstName,
        @Valid @NotNull(message = "{lastName.empty}") LastName lastName,
        @Valid @NotNull(message = "{address.empty}") Address address,
        @Valid @NotNull(message = "{postalCode.empty}") PostalCode postalCode,
        @Valid @NotNull(message = "{city.empty}") City city,
        @Valid @NotNull(message = "{pesel.invalid}") Pesel pesel,
        @Valid @NotNull(message = "{email.empty}") Email email,
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

}
