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
import java.util.*;
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

    public String generateJwtToken(UserDetails userDetails, Long id) {
        return generateTokenFromUsername(userDetails, id);
    }

    public String generateRefreshToken(UserDetails userDetails, Long id) {
        return generateRefreshTokenFromUsername(userDetails.getUsername(), id);
    }

    private String generateRefreshTokenFromUsername(String username, Long id) {
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(Instant.ofEpochMilli(ZonedDateTime.now(ZoneId.systemDefault())
                        .toInstant().toEpochMilli() + refreshTokenExpirationTime))
                .withClaim("id", id)
                .sign(Algorithm.HMAC256(secretKey));
    }

    private String generateTokenFromUsername(UserDetails userDetails, Long id) {
        if (userDetails.getAuthorities() != null){
            return JWT.create()
                    .withSubject(userDetails.getUsername())
                    .withExpiresAt(Instant.ofEpochMilli(ZonedDateTime.now(ZoneId.systemDefault())
                            .toInstant().toEpochMilli() + expirationTime))
                    .withClaim("roles", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                    .withClaim("id", id)
                    .sign(Algorithm.HMAC256(secretKey));
        }
        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withExpiresAt(Instant.ofEpochMilli(ZonedDateTime.now(ZoneId.systemDefault())
                        .toInstant().toEpochMilli() + expirationTime))
                .withClaim("id", id)
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

        return jwtData;
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

    public String getUsernameFromToken(String token) {
        String login = JWT.require(Algorithm.HMAC256(secretKey))
                .build()
                .verify(token.replace(tokenPrefix + " ", ""))
                .getSubject();
        return login;
    }
}
