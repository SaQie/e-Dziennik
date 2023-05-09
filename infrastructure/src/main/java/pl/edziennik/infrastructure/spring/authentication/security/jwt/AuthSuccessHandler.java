package pl.edziennik.infrastructure.spring.authentication.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import pl.edziennik.common.valueobject.Regon;
import pl.edziennik.domain.user.User;
import pl.edziennik.infrastructure.repositories.user.UserQueryRepository;
import pl.edziennik.infrastructure.spring.authentication.JwtUtils;
import pl.edziennik.infrastructure.spring.authentication.security.jwt.dto.AuthResponseDto;

import java.io.IOException;

/**
 * Handler for success login
 */
@Component
@Slf4j
public class AuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private UserQueryRepository repository;

    @Autowired
    private JwtUtils jwtUtils;

    @Value("${jwt.token.prefix}")
    private String tokenPrefix;


    @SneakyThrows
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        UserDetails principal = jwtUtils.getPrincipal(authentication);

        User user = repository.getByUsername(Regon.of(principal.getUsername()));

        String token = jwtUtils.generateJwtToken(principal, user.getUserId().id(), user.getSuperId());
        String refreshToken = jwtUtils.generateRefreshToken(principal, user.getUserId().id());

        AuthResponseDto authResponseDto = new AuthResponseDto();
        authResponseDto.setUsername(user.getUsername().value());
        authResponseDto.setToken(token);
        authResponseDto.setRefreshToken(refreshToken);


        // FIXME : CQRS
//        service.updateUserLastLoginDate(principal.getUsername());
        setRequiredHeadersAndPrintTokenToUser(response, authResponseDto);

    }

    private void setRequiredHeadersAndPrintTokenToUser(HttpServletResponse response, AuthResponseDto authResponseDto) throws IOException {
        response.addHeader("Authorization", tokenPrefix + " " + authResponseDto.getToken());
        response.addHeader("RefreshToken", authResponseDto.getRefreshToken());
        response.addHeader("Content-Type", "application/json");
        String jsonObject = new ObjectMapper().writeValueAsString(authResponseDto);
        response.getWriter().write(jsonObject);

    }
}
