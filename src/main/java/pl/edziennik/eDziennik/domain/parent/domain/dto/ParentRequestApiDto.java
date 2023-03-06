package pl.edziennik.eDziennik.domain.parent.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ParentRequestApiDto {

    public static final String USERNAME = "username";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String ADDRESS = "address";
    public static final String POSTAL_CODE = "postalCode";
    public static final String CITY = "city";
    public static final String PESEL = "pesel";
    public static final String ROLE = "role";
    public static final String EMAIL = "email";
    public static final String PHONE_NUMBER = "phoneNumber";
    public static final String ID_STUDENT = "idStudent";


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

    @Pattern(regexp = "[\\d]{9}", message = "{phone.invalid}")
    private String phoneNumber;

    @NotNull(message = "{student.empty}")
    private Long idStudent;

}
