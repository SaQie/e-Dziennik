package pl.edziennik.application.command.student.service.create;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import pl.edziennik.application.common.dispatcher.base.HandledBy;
import pl.edziennik.application.common.dispatcher.base.ValidatedBy;
import pl.edziennik.application.common.dispatcher.command.ICommand;
import pl.edziennik.domain.school.SchoolId;
import pl.edziennik.domain.schoolclass.SchoolClassId;
import pl.edziennik.domain.student.StudentId;

@HandledBy(handler = CreateStudentCommandHandler.class)
@ValidatedBy(validator = CreateStudentCommandValidator.class)
public record CreateStudentCommand(
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        @NotEmpty(message = "{password.empty}")
        @Size(min = 5, message = "{password.size}")
        String password,

        @NotEmpty(message = "{username.empty}")
        @Size(min = 4, message = "{username.size}")
        String username,

        @NotEmpty(message = "{firstName.empty}")
        String firstName,

        @NotEmpty(message = "{lastName.empty}")
        String lastName,

        @NotEmpty(message = "{address.empty}")
        String address,

        @NotEmpty(message = "{postalCode.empty}")
        @Size(min = 6, max = 6, message = "{postalCode.size}")
        String postalCode,

        @NotEmpty(message = "{city.empty}")
        String city,

        @org.hibernate.validator.constraints.pl.PESEL(message = "{pesel.invalid}")
        String pesel,

        @Email(message = "{email.is.not.valid}")
        @NotEmpty(message = "{email.empty}")
        String email,

        @Pattern(regexp = "[\\d]{9}", message = "{phone.invalid}")
        String phoneNumber,

        @NotNull(message = "{school.empty}")
        SchoolId schoolId,

        @NotNull(message = "{schoolClass.empty}")
        SchoolClassId schoolClassId

) implements ICommand<StudentId> {

    public static final String USERNAME = "username";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String ADDRESS = "address";
    public static final String POSTAL_CODE = "postalCode";
    public static final String CITY = "city";
    public static final String PESEL = "pesel";
    public static final String ROLE = "role";
    public static final String PHONE_NUMBER = "phoneNumber";
    public static final String ID_SCHOOL = "idSchool";
    public static final String ID_SCHOOL_CLASS = "idSchoolClass";
    public static final String EMAIL = "email";

}
