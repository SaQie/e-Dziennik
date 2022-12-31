package pl.edziennik.eDziennik.server.config.security.jwt.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import pl.edziennik.eDziennik.server.config.security.AccountType;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthResponseDto {

    private AccountType accountType;
    private String username;
    private String message;
    private String token;
    private String refreshToken;
}
