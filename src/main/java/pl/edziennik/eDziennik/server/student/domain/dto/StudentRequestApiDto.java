package pl.edziennik.eDziennik.server.student.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class StudentRequestApiDto{

    public static final String USERNAME = "username";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String ADDRESS = "address";
    public static final String POSTAL_CODE = "postalCode";
    public static final String CITY = "city";
    public static final String PESEL = "pesel";
    public static final String ROLE = "role";
    public static final String PARENT_PHONE_NUMBER = "parentPhoneNumber";
    public static final String ID_SCHOOL = "idSchool";
    public static final String ID_SCHOOL_CLASS = "idSchoolClass";
    public static final String PARENT_FIRST_NAME = "parentFirstName";
    public static final String PARENT_LAST_NAME = "parentLastName";
    public static final String EMAIL = "email";

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotEmpty(message = "{password.empty}")
    @Size(min = 5, message = "{password.size}")
    private String password;

    @NotEmpty(message = "{username.empty}")
    @Size(min = 4, message = "{username.size}")
    private String username;

    @NotEmpty(message = "{firstName.empty}")
    private String firstName;

    @NotEmpty(message = "{lastName.empty}")
    private String lastName;

    @NotEmpty(message = "{address.empty}")
    private String address;

    @NotEmpty(message = "{postalCode.empty}")
    @Size(min = 6, max = 6, message = "{postalCode.size}")
    private String postalCode;

    @NotEmpty(message = "{city.empty}")
    private String city;

    @org.hibernate.validator.constraints.pl.PESEL(message = "{pesel.invalid}")
    private String pesel;

    @Email(message = "{email.is.not.valid}")
    @NotEmpty(message = "{email.empty}")
    private String email;

    @NotEmpty(message = "{parentFirstName.empty}")
    private String parentFirstName;

    @NotEmpty(message = "{parentLastName.empty}")
    private String parentLastName;

    @NotEmpty(message = "{phoneNumber.empty}")
    private String parentPhoneNumber;

    @NotNull(message = "{school.empty}")
    private Long idSchool;

    @NotNull(message = "{schoolClass.empty}")
    private Long idSchoolClass;


}
