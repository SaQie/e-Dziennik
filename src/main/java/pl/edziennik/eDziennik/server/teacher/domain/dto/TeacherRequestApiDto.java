package pl.edziennik.eDziennik.server.teacher.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TeacherRequestApiDto{

    public static final String USERNAME = "username";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String ADDRESS = "username";
    public static final String POSTAL_CODE = "postalCode";
    public static final String CITY = "city";
    public static final String PESEL = "pesel";
    public static final String ROLE = "role";
    public static final String PHONE_NUMBER = "phoneNumber";
    public static final String ID_SCHOOL = "idSchool";
    public static final String EMAIL = "email";


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

    private String role;

    @NotEmpty(message = "{phone.empty}")
    @Pattern(regexp="[\\d]{9}", message = "{phone.invalid}")
    private String phoneNumber;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotEmpty(message = "{password.empty}")
    @Size(min = 4, message = "{password.size}")
    private String password;

    private Long idSchool;

}
