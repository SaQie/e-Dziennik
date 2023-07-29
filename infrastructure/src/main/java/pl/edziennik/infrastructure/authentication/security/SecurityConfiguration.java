package pl.edziennik.infrastructure.authentication.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import pl.edziennik.infrastructure.authentication.AuthUserDetailsService;
import pl.edziennik.infrastructure.authentication.JwtUtils;
import pl.edziennik.infrastructure.authentication.security.jwt.*;
import pl.edziennik.infrastructure.authentication.security.jwt.dto.AuthErrorDto;
import pl.edziennik.infrastructure.spring.ResourceCreator;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Security configuration
 */
@Configuration
public class SecurityConfiguration {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ResourceCreator resourceCreator;

    @Autowired
    private JwtUtils jwtUtils;

    private final AuthUserDetailsService authUserDetailsService;
    private final AuthSuccessHandler successHandler;
    private final AuthFailureHandler failureHandler;
    private final AuthLogoutHandler logoutHandler;


    public SecurityConfiguration(AuthUserDetailsService authUserDetailsService, AuthSuccessHandler successHandler,
                                 AuthFailureHandler failureHandler, AuthLogoutHandler logoutHandler) {
        this.authUserDetailsService = authUserDetailsService;
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
        this.logoutHandler = logoutHandler;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeHttpRequests((auth) -> {
                    try {
                        auth
                                .requestMatchers("/api/teachers/*").hasRole("ADMIN")
//                                .requestMatchers("/api/v1/schools").hasRole("ADMIN")
                                .requestMatchers("/api/students/").permitAll()
                                .requestMatchers("/api/schoolclasses/").permitAll()
                                .requestMatchers("/api/**").permitAll()
                                .requestMatchers("/jwt/**").permitAll()
                                .requestMatchers("/register").permitAll()
                                .requestMatchers("/teacher/moderator").hasRole("MODERATOR")
                                .requestMatchers("/teacher/teacher").hasRole("TEACHER")
                                .requestMatchers("/teacher/admin").hasRole("ADMIN")
                                .anyRequest()
                                .permitAll()
                                .and()
                                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                                .and()
                                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).addLogoutHandler(logoutHandler)
                                .and()
                                .addFilter(authenticationFilter())
                                .addFilter(new JwtAuthorizationFilter(authenticationManager, authUserDetailsService, jwtUtils))
                                .exceptionHandling()
                                .accessDeniedHandler(((request, response, accessDeniedException) -> {
                                    buildAccesDeniedResponse(request, response);
                                }))
                                .authenticationEntryPoint(((request, response, authException) -> {
                                    buildAuthenticationEntryPointResponse(request, response);
                                }));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public JsonObjectAuthenticationFilter authenticationFilter() {
        JsonObjectAuthenticationFilter jsonFilter = new JsonObjectAuthenticationFilter();
        jsonFilter.setAuthenticationSuccessHandler(successHandler);
        jsonFilter.setAuthenticationFailureHandler(failureHandler);
        jsonFilter.setAuthenticationManager(authenticationManager);
        return jsonFilter;
    }


    @SneakyThrows
    private void buildAccesDeniedResponse(HttpServletRequest request, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        prepareBasicResponse(request, response, resourceCreator.of("access.denied.message"));
    }


    @SneakyThrows
    private void buildAuthenticationEntryPointResponse(HttpServletRequest request, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        prepareBasicResponse(request, response, resourceCreator.of("unauthorized.message"));
    }

    private void prepareBasicResponse(HttpServletRequest request, HttpServletResponse response, String accessDeniedMessage) throws URISyntaxException, IOException {
        AuthErrorDto errorDto = new AuthErrorDto(accessDeniedMessage);
        String jsonObject = new ObjectMapper().writeValueAsString(errorDto);
        response.getWriter().write(jsonObject);
    }

}
