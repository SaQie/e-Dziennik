package pl.edziennik.application.command.student.create;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import pl.edziennik.application.common.dispatcher.Command;
import pl.edziennik.application.common.dispatcher.Handler;
import pl.edziennik.common.valueobject.id.SchoolClassId;
import pl.edziennik.common.valueobject.id.StudentId;
import pl.edziennik.common.valueobject.vo.*;

/**
 * A command used for creating a new student account
 */
@Handler(handler = CreateStudentCommandHandler.class, validator = CreateStudentCommandValidator.class)
public record CreateStudentCommand(

        @JsonIgnore StudentId studentId,

        @Schema(example = "SuperSecretPassword")
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
        @JsonIgnore SchoolClassId schoolClassId

) implements Command {

    public static final String USERNAME = "username";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String ADDRESS = "address";
    public static final String POSTAL_CODE = "postalCode";
    public static final String CITY = "city";
    public static final String PESEL = "pesel";
    public static final String ROLE = "role";
    public static final String PHONE_NUMBER = "phoneNumber";
    public static final String SCHOOL_CLASS_ID = "schoolClassId";
    public static final String EMAIL = "email";

    @JsonCreator
    public CreateStudentCommand(Password password, Username username, FirstName firstName, LastName lastName,
                                Address address, PostalCode postalCode, City city, Pesel pesel, Email email,
                                PhoneNumber phoneNumber, SchoolClassId schoolClassId) {
        this(StudentId.create(), password, username, firstName, lastName, address, postalCode, city, pesel, email, phoneNumber, schoolClassId);
    }

    public CreateStudentCommand(SchoolClassId schoolClassId, CreateStudentCommand command) {
        this(StudentId.create(), command.password, command.username, command.firstName, command.lastName, command.address,
                command.postalCode, command.city, command.pesel, command.email, command.phoneNumber, schoolClassId);
    }
}
