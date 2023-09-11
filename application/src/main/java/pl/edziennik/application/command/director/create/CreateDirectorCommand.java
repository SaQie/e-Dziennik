package pl.edziennik.application.command.director.create;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import pl.edziennik.application.common.dispatcher.Command;
import pl.edziennik.application.common.dispatcher.Handler;
import pl.edziennik.common.valueobject.id.DirectorId;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.valueobject.vo.*;

/**
 * A command used for creating a new director account
 */
@Handler(handler = CreateDirectorCommandHandler.class, validator = CreateDirectorCommandValidator.class)
public record CreateDirectorCommand(
        @JsonIgnore DirectorId directorId,
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
        @JsonIgnore SchoolId schoolId
) implements Command {


    public static final String USERNAME = "username";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String ADDRESS = "username";
    public static final String POSTAL_CODE = "postalCode";
    public static final String CITY = "city";
    public static final String PESEL = "pesel";
    public static final String PHONE_NUMBER = "phoneNumber";
    public static final String SCHOOL_ID = "schoolId";
    public static final String EMAIL = "email";


    @JsonCreator
    public CreateDirectorCommand(Password password, Username username, FirstName firstName, LastName lastName, Address address,
                                 PostalCode postalCode, City city, Pesel pesel,
                                 Email email, PhoneNumber phoneNumber, SchoolId schoolId) {
        this(DirectorId.create(), password, username, firstName, lastName, address, postalCode, city, pesel, email, phoneNumber, schoolId);
    }

    public CreateDirectorCommand(SchoolId schoolId, CreateDirectorCommand command) {
        this(DirectorId.create(), command.password, command.username, command.firstName, command.lastName, command.address, command.postalCode,
                command.city, command.pesel, command.email, command.phoneNumber, schoolId);
    }
}
