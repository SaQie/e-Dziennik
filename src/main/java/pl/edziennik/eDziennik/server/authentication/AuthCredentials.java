package pl.edziennik.eDziennik.server.authentication;

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
