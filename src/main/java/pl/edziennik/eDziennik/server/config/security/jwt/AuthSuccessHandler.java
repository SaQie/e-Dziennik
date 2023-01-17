package pl.edziennik.eDziennik.server.config.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import pl.edziennik.eDziennik.server.basics.ApiResponse;
import pl.edziennik.eDziennik.server.config.security.AccountType;
import pl.edziennik.eDziennik.server.config.security.jwt.dto.AuthResponseDto;
import pl.edziennik.eDziennik.server.user.domain.User;
import pl.edziennik.eDziennik.server.user.dao.UserDao;
import pl.edziennik.eDziennik.server.user.services.UserService;
import pl.edziennik.eDziennik.server.utils.JwtUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;

@Component
@Slf4j
public class AuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private UserDao dao;

    @Autowired
    private UserService service;

    @Autowired
    private JwtUtils jwtUtils;

    @Value("${jwt.token.prefix}")
    private String tokenPrefix;


    @SneakyThrows
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserDetails principal = jwtUtils.getPrincipal(authentication);
        User user = dao.getByUsername(principal.getUsername());
        String token = jwtUtils.generateJwtToken(principal, user.getId());
        String refreshToken = jwtUtils.generateRefreshToken(principal, user.getId());
        AuthResponseDto authResponseDto = new AuthResponseDto();
        authResponseDto.setAccountType(AccountType.STUDENT);
        authResponseDto.setUsername(user.getUsername());
        authResponseDto.setToken(token);
        authResponseDto.setRefreshToken(refreshToken);
        ApiResponse<AuthResponseDto> apiResponseDto = ApiResponse.buildApiResponse(HttpMethod.POST, HttpStatus.OK, authResponseDto, new URI(request.getRequestURI()));
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
