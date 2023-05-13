package pl.edziennik.application.command.parent.create;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.application.common.dispatcher.base.HandledBy;
import pl.edziennik.application.common.dispatcher.base.ValidatedBy;
import pl.edziennik.application.common.dispatcher.command.ICommand;
import pl.edziennik.common.valueobject.*;
import pl.edziennik.common.valueobject.id.StudentId;

@HandledBy(handler = CreateParentCommandHandler.class)
@ValidatedBy(validator = CreateParentCommandValidator.class)
public record CreateParentCommand(

        @Valid @NotNull(message = "{password.empty}") Password password,
        @Valid @NotNull(message = "{username.empty}") Username username,
        @Valid @NotNull(message = "{firstName.empty}") FirstName firstName,
        @Valid @NotNull(message = "{lastName.empty}") LastName lastName,
        @Valid @NotNull(message = "{address.empty}") Address address,
        @Valid @NotNull(message = "{postalCode.empty}") PostalCode postalCode,
        @Valid @NotNull(message = "{city.empty}") City city,
        @Valid @NotNull(message = "{pesel.invalid}") Pesel pesel,
        @Valid @NotNull(message = "{email.empty}") pl.edziennik.common.valueobject.Email email,
        @Valid @NotNull(message = "{phone.invalid}") PhoneNumber phoneNumber,
        StudentId studentId

) implements ICommand<OperationResult> {

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
    public static final String STUDENT_ID = "studentID";

}
