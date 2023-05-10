package pl.edziennik.infrastructure.spring.authentication;

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
import java.util.*;
import java.util.stream.Collectors;

/**
 * Utility class for jwt token authentication
 */
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

    public String generateJwtToken(UserDetails userDetails, UUID id, UUID superId) {
        return generateTokenFromUsername(userDetails, id, superId);
    }

    public String generateRefreshToken(UserDetails userDetails, UUID id) {
        return generateRefreshTokenFromUsername(userDetails.getUsername(), id);
    }

    private String generateRefreshTokenFromUsername(String username, UUID id) {
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(Instant.ofEpochMilli(ZonedDateTime.now(ZoneId.systemDefault())
                        .toInstant().toEpochMilli() + refreshTokenExpirationTime))
                .withClaim("id", id.toString())
                .sign(Algorithm.HMAC256(secretKey));
    }

    private String generateTokenFromUsername(UserDetails userDetails, UUID id, UUID superId) {
        if (userDetails.getAuthorities() != null) {
            return JWT.create()
                    .withSubject(userDetails.getUsername())
                    .withExpiresAt(Instant.ofEpochMilli(ZonedDateTime.now(ZoneId.systemDefault())
                            .toInstant().toEpochMilli() + expirationTime))
                    .withClaim("roles", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                    .withClaim("id", id.toString())
                    .withClaim("superId", superId.toString())
                    .sign(Algorithm.HMAC256(secretKey));
        }
        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withExpiresAt(Instant.ofEpochMilli(ZonedDateTime.now(ZoneId.systemDefault())
                        .toInstant().toEpochMilli() + expirationTime))
                .withClaim("id", id.toString())
                .withClaim("superId", superId.toString())
                .sign(Algorithm.HMAC256(secretKey));
    }


    public UserDetails getPrincipal(Authentication authentication) {
        return (UserDetails) authentication.getPrincipal();
    }

    public Map<String, String> getDataFromToken(String token) {
        Map<String, String> jwtData = new HashMap<>();
        String login = JWT.require(Algorithm.HMAC256(secretKey))
                .build()
                .verify(token.replace(tokenPrefix + " ", ""))
                .getSubject();
        jwtData.put("username", login);

        String id = JWT.require(Algorithm.HMAC256(secretKey))
                .build()
                .verify(token.replace(tokenPrefix + " ", ""))
                .getClaim("id").asString();
        jwtData.put("id", id);

        String superId = JWT.require(Algorithm.HMAC256(secretKey))
                .build()
                .verify(token.replace(tokenPrefix + " ", ""))
                .getClaim("superId").asString();
        jwtData.put("superId", superId);

        return jwtData;
    }

    public boolean isTokenNotExist(String token) {
        return token == null || !token.startsWith(tokenPrefix);
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

    public String getUsernameFromToken(String token) {
        return JWT.require(Algorithm.HMAC256(secretKey))
                .build()
                .verify(token.replace(tokenPrefix + " ", ""))
                .getSubject();
    }
}
