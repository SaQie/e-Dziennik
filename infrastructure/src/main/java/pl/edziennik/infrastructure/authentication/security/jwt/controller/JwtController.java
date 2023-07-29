package pl.edziennik.infrastructure.authentication.security.jwt.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.edziennik.infrastructure.authentication.AuthCredentials;
import pl.edziennik.infrastructure.authentication.JwtService;
import pl.edziennik.infrastructure.authentication.security.jwt.dto.AuthErrorDto;
import pl.edziennik.infrastructure.authentication.security.jwt.dto.AuthResponseDto;

import java.io.IOException;

@RestController
@AllArgsConstructor
public class JwtController {

    private final String AUTHORIZATION = "Authorization";

    private final JwtService jwtService;

    @PostMapping("/login")
    @ApiResponses({
            @ApiResponse(responseCode = "401", content = @Content(schema = @Schema(implementation = AuthErrorDto.class))),
            @ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = AuthErrorDto.class))),
            @ApiResponse(responseCode = "400", content = @Content),
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = AuthResponseDto.class)))
    })
    public void login(@RequestBody AuthCredentials authCredentials) {

    }

    @GetMapping("/login")
    public void loginDefaultEndpoint() {

    }

    @PostMapping("/jwt/refreshtoken")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String refreshToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        String token = jwtService.validateRefreshTokenAndReturnNew(refreshToken);
        setRequiredHeaders(response, refreshToken.substring("Bearer ".length()), token);
    }

    @PostMapping("/jwt/validate")
    public boolean validateToken(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        return jwtService.isTokenValid(token);
    }

    private void setRequiredHeaders(HttpServletResponse response, String refreshToken, String token) throws IOException {
        response.addHeader("Authorization", "Bearer " + token);
        response.addHeader("RefreshToken", refreshToken);
        response.addHeader("Content-Type", "application/json");
        response.getWriter().write("{\"token\": \"" + token + "\"}");
        response.getWriter().write("{\"refresh_token\": \"" + refreshToken + "\"}");
    }

}
