package pl.edziennik.eDziennik.domain.admin.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record AdminRequestApiDto(
        @NotEmpty(message = "{username.empty}")
        String username,
        @Email(message = "{email.is.not.valid}")
        @NotEmpty(message = "{email.empty}")
        String email,
        @NotEmpty(message = "{password.empty}")
        String password
) {

    public static final String USERNAME = "username";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";


}
