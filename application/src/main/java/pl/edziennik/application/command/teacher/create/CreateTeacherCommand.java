package pl.edziennik.application.command.teacher.create;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import pl.edziennik.application.common.dispatcher.Command;
import pl.edziennik.application.common.dispatcher.Handler;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.valueobject.id.TeacherId;
import pl.edziennik.common.valueobject.vo.*;

/**
 * A command used for creating a new teacher
 */
@Handler(handler = CreateTeacherCommandHandler.class, validator = CreateTeacherCommandValidator.class)
public record CreateTeacherCommand(

        @JsonIgnore TeacherId teacherId,
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
        SchoolId schoolId

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
    public CreateTeacherCommand(Password password, Username username, FirstName firstName, LastName lastName, Address address, PostalCode postalCode,
                                City city, Pesel pesel, Email email, PhoneNumber phoneNumber, SchoolId schoolId) {
        this(TeacherId.create(), password, username, firstName, lastName, address, postalCode, city, pesel, email, phoneNumber, schoolId);
    }

    public CreateTeacherCommand(SchoolId schoolId, CreateTeacherCommand command) {
        this(TeacherId.create(), command.password, command.username, command.firstName, command.lastName, command.address,
                command.postalCode, command.city, command.pesel, command.email, command.phoneNumber, schoolId);
    }
}
