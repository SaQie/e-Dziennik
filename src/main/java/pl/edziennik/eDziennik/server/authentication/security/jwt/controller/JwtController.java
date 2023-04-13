package pl.edziennik.eDziennik.server.authentication.security.jwt.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edziennik.eDziennik.server.authentication.JwtService;

import java.io.IOException;

@RestController
@AllArgsConstructor
public class JwtController {

    private final String AUTHORIZATION = "Authorization";

    private final JwtService jwtService;

    @PostMapping("/jwt/refreshtoken")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String refreshToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        String token = jwtService.validateRefreshTokenAndReturnNew(refreshToken);
        setRequiredHeaders(response,refreshToken.substring("Bearer ".length()),token);
    }

    private void setRequiredHeaders(HttpServletResponse response, String refreshToken, String token) throws IOException {
        response.addHeader("Authorization", "Bearer " + token);
        response.addHeader("RefreshToken", refreshToken);
        response.addHeader("Content-Type", "application/json");
        response.getWriter().write("{\"token\": \""+ token +"\"}");
        response.getWriter().write("{\"refresh_token\": \""+ refreshToken +"\"}");
    }

}
