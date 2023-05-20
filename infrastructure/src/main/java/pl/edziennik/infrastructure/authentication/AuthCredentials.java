package pl.edziennik.infrastructure.authentication;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class AuthCredentials {

    private String username;
    private String password;

}
