package pl.edziennik.application.command.student.create;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.*;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.application.common.dispatcher.base.HandledBy;
import pl.edziennik.application.common.dispatcher.base.ValidatedBy;
import pl.edziennik.application.common.dispatcher.command.ICommand;
import pl.edziennik.common.valueobject.*;
import pl.edziennik.common.valueobject.id.SchoolClassId;
import pl.edziennik.common.valueobject.id.SchoolId;

@HandledBy(handler = CreateStudentCommandHandler.class)
@ValidatedBy(validator = CreateStudentCommandValidator.class)
public record CreateStudentCommand(
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        @NotEmpty(message = "{password.empty}")
        @Size(min = 5, message = "{password.size}")
        Password password,

        @NotEmpty(message = "{username.empty}")
        @Size(min = 4, message = "{username.size}")
        Username username,

        @NotEmpty(message = "{firstName.empty}")
        FirstName firstName,

        @NotEmpty(message = "{lastName.empty}")
        LastName lastName,

        @NotEmpty(message = "{address.empty}")
        Address address,

        @NotEmpty(message = "{postalCode.empty}")
        @Size(min = 6, max = 6, message = "{postalCode.size}")
        PostalCode postalCode,

        @NotEmpty(message = "{city.empty}")
        City city,

        @org.hibernate.validator.constraints.pl.PESEL(message = "{pesel.invalid}")
        Pesel pesel,

        @Email(message = "{email.is.not.valid}")
        @NotEmpty(message = "{email.empty}")
        pl.edziennik.common.valueobject.Email email,

        @Pattern(regexp = "[\\d]{9}", message = "{phone.invalid}")
        PhoneNumber phoneNumber,

        @NotNull(message = "{school.empty}")
        SchoolId schoolId,

        @NotNull(message = "{schoolClass.empty}")
        SchoolClassId schoolClassId

) implements ICommand<OperationResult> {

    public static final String USERNAME = "username";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String ADDRESS = "address";
    public static final String POSTAL_CODE = "postalCode";
    public static final String CITY = "city";
    public static final String PESEL = "pesel";
    public static final String ROLE = "role";
    public static final String PHONE_NUMBER = "phoneNumber";
    public static final String SCHOOL_ID = "schoolId";
    public static final String SCHOOL_CLASS_ID = "schoolClassId";
    public static final String EMAIL = "email";

}
