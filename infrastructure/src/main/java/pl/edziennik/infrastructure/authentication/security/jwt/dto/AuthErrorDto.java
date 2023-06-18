package pl.edziennik.infrastructure.authentication.security.jwt.dto;

public class AuthErrorDto {

    private final String message;

    public String getMessage() {
        return message;
    }

    public AuthErrorDto(String message) {
        this.message = message;
    }


}
