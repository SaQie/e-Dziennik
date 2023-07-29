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
import pl.edziennik.infrastructure.authentication.security.LoggedUser;
import pl.edziennik.infrastructure.spring.cache.SpringCacheService;

import java.util.Date;
import java.util.Map;


/**
 * Handler for logout action
 */
@Component
@Slf4j
public class AuthLogoutHandler implements LogoutHandler {

    @Autowired
    private SpringCacheService springCacheService;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        response.setHeader(HttpHeaders.AUTHORIZATION, null);

        if (token == null || token.isEmpty()) {
            return;
        }

        Map<String, Object> dataFromToken = jwtUtils.getDataFromToken(token);
        String id = (String) dataFromToken.get("id");
        Date expirationDate = (Date) dataFromToken.get("expirationDate");

        LoggedUser loggedUser = new LoggedUser(id, expirationDate);

        springCacheService.deleteLoggedUser(loggedUser);

    }
}
