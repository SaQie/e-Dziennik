package pl.edziennik.eDziennik.server.authentication;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.server.utils.JwtUtils;

import java.util.Map;

@Service
@AllArgsConstructor
public class JwtService {

    private final JwtUtils jwtUtils;
    private final AuthUserDetailsService authUserDetailsService;

    public String validateRefreshTokenAndReturnNew(String refreshToken){
        if (jwtUtils.isTokenNotExist(refreshToken)){
            throw new RuntimeException("refresh token is missing");
        }
        Map<String, String> jwtData = jwtUtils.getDataFromToken(refreshToken);
        String username = jwtData.get("username");
        if (username == null) {
            throw new RuntimeException("login is missing");
        }
        UserDetails userDetails = authUserDetailsService.loadUserByUsername(username);
        Long id = Long.valueOf(jwtData.get("id"));
        Long superId = Long.valueOf(jwtData.get("superId"));
        return jwtUtils.generateJwtToken(userDetails, id, superId);
    }

}
