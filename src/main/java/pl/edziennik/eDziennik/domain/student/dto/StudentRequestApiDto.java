package pl.edziennik.eDziennik.domain.student.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;
import pl.edziennik.eDziennik.domain.school.domain.wrapper.SchoolId;
import pl.edziennik.eDziennik.domain.schoolclass.domain.wrapper.SchoolClassId;


@Builder
@Getter
@Accessors(fluent = true)
@AllArgsConstructor
public class StudentRequestApiDto {
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotEmpty(message = "{password.empty}")
    @Size(min = 5, message = "{password.size}")
    private final String password;

    @NotEmpty(message = "{username.empty}")
    @Size(min = 4, message = "{username.size}")
    private final String username;

    @NotEmpty(message = "{firstName.empty}")
    private final String firstName;

    @NotEmpty(message = "{lastName.empty}")
    private final String lastName;

    @NotEmpty(message = "{address.empty}")
    private final String address;

    @NotEmpty(message = "{postalCode.empty}")
    @Size(min = 6, max = 6, message = "{postalCode.size}")
    private final String postalCode;

    @NotEmpty(message = "{city.empty}")
    private final String city;

    @org.hibernate.validator.constraints.pl.PESEL(message = "{pesel.invalid}")
    private final String pesel;

    @Email(message = "{email.is.not.valid}")
    @NotEmpty(message = "{email.empty}")
    private final String email;

    @Pattern(regexp = "[\\d]{9}", message = "{phone.invalid}")
    private final String phoneNumber;

    @NotNull(message = "{school.empty}")
    private final SchoolId schoolId;

    @NotNull(message = "{schoolClass.empty}")
    private final SchoolClassId schoolClassId;

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
