package pl.edziennik.eDziennik.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserRequestDto {

    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String EMAIL = "email";
    public static final String ROLE = "role";

    private String username;
    private String password;
    private String email;
    private String role;

    private String firstName;
    private String lastName;
    private String pesel;

    private String address;
    private String postalCode;
    private String city;

}
