package pl.edziennik.infrastructure.authentication.security.jwt;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import pl.edziennik.infrastructure.authentication.JwtUtils;


/**
 * Handler for logout action
 */
@Component
@Slf4j
public class AuthLogoutHandler implements LogoutHandler {

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        response.setHeader(HttpHeaders.AUTHORIZATION, null);

        if (token == null || token.isEmpty()) {
            return;
        }

        jwtUtils.deleteLoggedUserFromCache(token);
    }

}
