package pl.edziennik.eDziennik.domain.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
