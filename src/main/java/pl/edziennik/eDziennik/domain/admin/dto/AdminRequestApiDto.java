package pl.edziennik.eDziennik.domain.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AdminRequestApiDto {

    public static final String USERNAME = "username";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";

    @NotEmpty(message = "{username.empty}")
    private String username;
    @Email(message = "{email.is.not.valid}")
    @NotEmpty(message = "{email.empty}")
    private String email;
    @NotEmpty(message = "{password.empty}")
    private String password;

}
