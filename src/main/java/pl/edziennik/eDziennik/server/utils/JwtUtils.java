package pl.edziennik.eDziennik.server.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private int expirationTime;

    @Value("${jwt.refresh.expiration}")
    private int refreshTokenExpirationTime;

    @Value("${jwt.token.prefix}")
    private String tokenPrefix;

    public String generateJwtToken(UserDetails userDetails) {
        return generateTokenFromUsername(userDetails);
    }

    public String generateRefreshToken(UserDetails userDetails) {
        return generateRefreshTokenFromUsername(userDetails.getUsername());
    }

    private String generateRefreshTokenFromUsername(String username) {
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(Instant.ofEpochMilli(ZonedDateTime.now(ZoneId.systemDefault())
                        .toInstant().toEpochMilli() + refreshTokenExpirationTime))
                .sign(Algorithm.HMAC256(secretKey));
    }

    private String generateTokenFromUsername(UserDetails userDetails) {
        if (userDetails.getAuthorities() != null){
            return JWT.create()
                    .withSubject(userDetails.getUsername())
                    .withExpiresAt(Instant.ofEpochMilli(ZonedDateTime.now(ZoneId.systemDefault())
                            .toInstant().toEpochMilli() + expirationTime))
                    .withClaim("roles", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                    .sign(Algorithm.HMAC256(secretKey));
        }
        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withExpiresAt(Instant.ofEpochMilli(ZonedDateTime.now(ZoneId.systemDefault())
                        .toInstant().toEpochMilli() + expirationTime))
                .sign(Algorithm.HMAC256(secretKey));
    }


    public UserDetails getPrincipal(Authentication authentication) {
        return (UserDetails) authentication.getPrincipal();
    }

    public String getUsernameFromToken(String token) {
        String login = JWT.require(Algorithm.HMAC256(secretKey))
                .build()
                .verify(token.replace(tokenPrefix + " ", ""))
                .getSubject();
        return login;
    }

    public boolean isTokenNotExist(String token) {
        if (token == null || !token.startsWith(tokenPrefix)) {
            return true;
        }
        return false;
    }

    public Collection<SimpleGrantedAuthority> getRolesFromTokenIfNeeded(String token) {
        String[] roles = JWT.require(Algorithm.HMAC256(secretKey))
                .build()
                .verify(token.replace(tokenPrefix + " ", ""))
                .getClaim("roles")
                .asArray(String.class);
        if (roles != null) {
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            Arrays.stream(roles).forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
            return authorities;
        }
        return null;
    }
}
