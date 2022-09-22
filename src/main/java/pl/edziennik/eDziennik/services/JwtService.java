package pl.edziennik.eDziennik.services;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.security.JwtUtils;

@Service
@AllArgsConstructor
public class JwtService {

    private final JwtUtils jwtUtils;
    private final AuthUserDetailsService authUserDetailsService;

    public String validateRefreshTokenAndReturnNew(String refreshToken){
        if (jwtUtils.isTokenNotExist(refreshToken)){
            throw new RuntimeException("refresh token is missing");
        }
        String login = jwtUtils.getUsernameFromToken(refreshToken);
        if (login == null) {
            throw new RuntimeException("login is missing");
        }
        UserDetails userDetails = authUserDetailsService.loadUserByUsername(login);
        return jwtUtils.generateJwtToken(userDetails);


    }

}
