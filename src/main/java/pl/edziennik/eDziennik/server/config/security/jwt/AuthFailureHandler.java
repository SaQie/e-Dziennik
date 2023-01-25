package pl.edziennik.eDziennik.server.config.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import pl.edziennik.eDziennik.server.basics.ApiResponse;
import pl.edziennik.eDziennik.server.basics.ApiResponseCreator;
import pl.edziennik.eDziennik.server.config.security.jwt.dto.AuthResponseDto;
import pl.edziennik.eDziennik.server.utils.ResourceCreator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;

@Component
@Slf4j
public class AuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private ResourceCreator resourceCreator;

    @SneakyThrows
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.addHeader("Content-Type", "application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        AuthResponseDto authResponseDto = new AuthResponseDto();
        authResponseDto.setMessage(resourceCreator.of("bad.credentials.message"));
        ApiResponse<AuthResponseDto> apiResponseDto = ApiResponseCreator.buildApiResponse(HttpMethod.POST, HttpStatus.UNAUTHORIZED, authResponseDto, new URI(request.getRequestURI()));
        String jsonObject = new ObjectMapper().writeValueAsString(apiResponseDto);
        response.getWriter().write(jsonObject);
    }
}
