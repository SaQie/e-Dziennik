package pl.edziennik.eDziennik.domain.admin.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor
public class AdminRequestApiDto {
    @NotEmpty(message = "{username.empty}")
    private final String username;
    @Email(message = "{email.is.not.valid}")
    @NotEmpty(message = "{email.empty}")
    private final String email;
    @NotEmpty(message = "{password.empty}")
    private final String password;

    public static final String USERNAME = "username";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";


}
