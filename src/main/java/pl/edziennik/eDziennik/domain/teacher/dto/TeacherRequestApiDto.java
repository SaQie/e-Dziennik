package pl.edziennik.eDziennik.domain.teacher.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Builder
public record TeacherRequestApiDto(
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

        String role,

        @Pattern(regexp = "[\\d]{9}", message = "{phone.invalid}")
        String phoneNumber,

        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        @NotEmpty(message = "{password.empty}")
        @Size(min = 4, message = "{password.size}")
        String password,

        Long idSchool
) {

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


}
