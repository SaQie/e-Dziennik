package pl.edziennik.infrastructure.authentication.security.jwt.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthResponseDto {

    private String username;
    private String message;
    private String token;
    private String refreshToken;

}
