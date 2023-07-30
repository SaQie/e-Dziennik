package pl.edziennik.infrastructure.authentication.security;

import java.util.Date;


public record LoggedUser(

        Date expirationDate,
        String userId

) {
}
