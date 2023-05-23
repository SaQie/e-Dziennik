package pl.edziennik.infrastructure.authentication.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import pl.edziennik.infrastructure.authentication.security.jwt.dto.AuthResponseDto;
import pl.edziennik.infrastructure.spring.ResourceCreator;

/**
 * Handler for failure login (bad credentials)
 */
@Component
@Slf4j
public class AuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private ResourceCreator resourceCreator;


    @SneakyThrows
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
        response.addHeader("Content-Type", "application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        AuthResponseDto authResponseDto = new AuthResponseDto();
        authResponseDto.setMessage(resourceCreator.of("bad.credentials.message"));

        if (exception instanceof DisabledException){
            authResponseDto.setMessage(resourceCreator.of("account.disabled"));
        }
        String jsonObject = new ObjectMapper().writeValueAsString(authResponseDto);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonObject);
    }
}
