package pl.edziennik.eDziennik.server.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import pl.edziennik.eDziennik.server.config.security.jwt.*;
import pl.edziennik.eDziennik.server.utils.JwtUtils;
import pl.edziennik.eDziennik.authentication.AuthUserDetailsService;

import javax.servlet.http.HttpServletResponse;

@Configuration
public class SecurityConfiguration {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;

    private final AuthUserDetailsService authUserDetailsService;
    private final AuthSuccessHandler successHandler;
    private final AuthFailureHandler failureHandler;
    private final AuthLogoutHandler logoutHandler;
    private final String unauthorizedMessage;
    private final String accessDeniedMessage;


    public SecurityConfiguration(AuthUserDetailsService authUserDetailsService, AuthSuccessHandler successHandler,
                                 AuthFailureHandler failureHandler, AuthLogoutHandler logoutHandler, @Value("${jwt.unauthorized.message}") String unauthorizedMessage,
                                 @Value("${jwt.accessDenied.message}") String accessDeniedMessage) {
        this.authUserDetailsService = authUserDetailsService;
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
        this.logoutHandler = logoutHandler;
        this.unauthorizedMessage = unauthorizedMessage;
        this.accessDeniedMessage = accessDeniedMessage;
    }

    @Configuration
    @Order(1)
    public class StudentSpringSecurityConfiguration{

        @Bean
        public SecurityFilterChain configureStudentHttpSecurity(HttpSecurity http) throws Exception{
            http
                    .cors()
                    .and()
                    .csrf()
                    .disable()
                    .authorizeHttpRequests((auth) -> {
                        try {
                            auth
                                    .antMatchers("/api/teachers").permitAll()
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
                                        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                                        response.getWriter().println(accessDeniedMessage);
                                    }))
                                    .authenticationEntryPoint(((request, response, authException) -> {
                                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                                        response.getWriter().println(unauthorizedMessage);
                                    }));
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .httpBasic(Customizer.withDefaults());
            return http.build();
        }
    }


    @Bean
    public JsonObjectAuthenticationFilter authenticationFilter(){
        JsonObjectAuthenticationFilter jsonFilter = new JsonObjectAuthenticationFilter();
        jsonFilter.setAuthenticationSuccessHandler(successHandler);
        jsonFilter.setAuthenticationFailureHandler(failureHandler);
        jsonFilter.setAuthenticationManager(authenticationManager);
        return jsonFilter;
    }

}
