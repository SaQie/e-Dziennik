package pl.edziennik.eDziennik.server.authentication.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import pl.edziennik.eDziennik.domain.user.domain.User;
import pl.edziennik.eDziennik.domain.user.repository.UserRepository;
import pl.edziennik.eDziennik.domain.user.services.UserService;
import pl.edziennik.eDziennik.server.authentication.security.jwt.dto.AuthResponseDto;
import pl.edziennik.eDziennik.server.basics.dto.ApiResponse;
import pl.edziennik.eDziennik.server.basics.dto.ApiResponseCreator;
import pl.edziennik.eDziennik.server.utils.JwtUtils;

import java.io.IOException;
import java.net.URI;

/**
 * Handler for success login
 */
@Component
@Slf4j
public class AuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserService service;

    @Autowired
    private JwtUtils jwtUtils;

    @Value("${jwt.token.prefix}")
    private String tokenPrefix;


    @SneakyThrows
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication){
        UserDetails principal = jwtUtils.getPrincipal(authentication);
        User user = repository.getByUsername(principal.getUsername());
        String token = jwtUtils.generateJwtToken(principal, user.getUserId().id());
        String refreshToken = jwtUtils.generateRefreshToken(principal, user.getUserId().id());
        AuthResponseDto authResponseDto = new AuthResponseDto();
        authResponseDto.setUsername(user.getUsername());
        authResponseDto.setToken(token);
        authResponseDto.setRefreshToken(refreshToken);
        ApiResponse<AuthResponseDto> apiResponseDto = ApiResponseCreator.buildApiResponse(HttpMethod.POST, HttpStatus.OK, authResponseDto, new URI(request.getRequestURI()));
        service.updateUserLastLoginDate(principal.getUsername());
        setRequiredHeadersAndPrintTokenToUser(response, apiResponseDto);

    }

    private void setRequiredHeadersAndPrintTokenToUser(HttpServletResponse response, ApiResponse<AuthResponseDto> authResponseDto) throws IOException {
        response.addHeader("Authorization", tokenPrefix + " " + authResponseDto.getResult().getToken());
        response.addHeader("RefreshToken", authResponseDto.getResult().getRefreshToken());
        response.addHeader("Content-Type", "application/json");
        String jsonObject = new ObjectMapper().writeValueAsString(authResponseDto);
        response.getWriter().write(jsonObject);

    }
}
