package pl.edziennik.eDziennik.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
public record UserRequestDto(
        String username,
        String password,
        String email,
        String role
) {

    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String EMAIL = "email";
    public static final String ROLE = "role";



}
