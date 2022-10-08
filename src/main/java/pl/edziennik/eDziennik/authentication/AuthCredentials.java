package pl.edziennik.eDziennik.authentication;

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
