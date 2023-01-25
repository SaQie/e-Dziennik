package pl.edziennik.eDziennik.server.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import pl.edziennik.eDziennik.server.basics.ApiResponse;
import pl.edziennik.eDziennik.server.basics.ApiResponseCreator;
import pl.edziennik.eDziennik.server.config.security.jwt.*;
import pl.edziennik.eDziennik.server.config.security.jwt.dto.AuthResponseDto;
import pl.edziennik.eDziennik.server.utils.JwtUtils;
import pl.edziennik.eDziennik.authentication.AuthUserDetailsService;
import pl.edziennik.eDziennik.server.utils.ResourceCreator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

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
                                 AuthFailureHandler failureHandler, AuthLogoutHandler logoutHandler
                                 ) {
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
                                .antMatchers("/api/teachers/*").hasRole("ADMIN")
                                .antMatchers("/api/schools").permitAll()
                                .antMatchers("/api/students").permitAll()
                                .antMatchers("/api/schoolclasses").permitAll()
                                .antMatchers("/api/**").permitAll()
                                .antMatchers("/jwt/**").permitAll()
                                .antMatchers("/register").permitAll()
                                .antMatchers("/teacher/moderator").hasRole("MODERATOR")
                                .antMatchers("/teacher/teacher").hasRole("TEACHER")
                                .antMatchers("/teacher/admin").hasRole("ADMIN")
                                .anyRequest()
                                .permitAll()
                                .and()
                                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                                .and()
                                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/").addLogoutHandler(logoutHandler)
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
        AuthResponseDto authResponseDto = new AuthResponseDto();
        authResponseDto.setMessage(accessDeniedMessage);
        ApiResponse<AuthResponseDto> apiResponseDto = ApiResponseCreator.buildApiResponse(HttpMethod.valueOf(request.getMethod()), HttpStatus.valueOf(response.getStatus()), authResponseDto, new URI(request.getRequestURI()));
        String jsonObject = new ObjectMapper().writeValueAsString(apiResponseDto);
        response.getWriter().write(jsonObject);
    }


}
