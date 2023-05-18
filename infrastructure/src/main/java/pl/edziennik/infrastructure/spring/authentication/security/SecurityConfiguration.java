package pl.edziennik.infrastructure.spring.authentication.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * Security configuration
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

//    private final AuthenticationManager authenticationManager;
//    private final ResourceCreator resourceCreator;
//    private final JwtUtils jwtUtils;
//
//    private final AuthUserDetailsService authUserDetailsService;
//    private final AuthSuccessHandler successHandler;
//    private final AuthFailureHandler failureHandler;
//    private final AuthLogoutHandler logoutHandler;
//
//    @Autowired
//    public SecurityConfiguration(AuthenticationManager authenticationManager, ResourceCreator resourceCreator, JwtUtils jwtUtils, AuthUserDetailsService authUserDetailsService, AuthSuccessHandler successHandler,
//                                 AuthFailureHandler failureHandler, AuthLogoutHandler logoutHandler
//    ) {
//        this.authenticationManager = authenticationManager;
//        this.resourceCreator = resourceCreator;
//        this.jwtUtils = jwtUtils;
//        this.authUserDetailsService = authUserDetailsService;
//        this.successHandler = successHandler;
//        this.failureHandler = failureHandler;
//        this.logoutHandler = logoutHandler;
//    }
//
//    @Bean
//    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
//        http
//                .cors()
//                .and()
//                .csrf()
//                .disable()
//                .authorizeHttpRequests((auth) -> {
//                    try {
//                        auth
//                                .requestMatchers("/api/teachers/*").hasRole("ADMIN")
//                                .requestMatchers("/api/schools/").permitAll()
//                                .requestMatchers("/api/students/").permitAll()
//                                .requestMatchers("/api/schoolclasses/").permitAll()
//                                .requestMatchers("/api/**").permitAll()
//                                .requestMatchers("/jwt/**").permitAll()
//                                .requestMatchers("/register").permitAll()
//                                .requestMatchers("/teacher/moderator").hasRole("MODERATOR")
//                                .requestMatchers("/teacher/teacher").hasRole("TEACHER")
//                                .requestMatchers("/teacher/admin").hasRole("ADMIN")
//                                .anyRequest()
//                                .permitAll()
//                                .and()
//                                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                                .and()
//                                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/").addLogoutHandler(logoutHandler)
//                                .and()
//                                .addFilter(authenticationFilter())
//                                .addFilter(new JwtAuthorizationFilter(authenticationManager, authUserDetailsService, jwtUtils))
//                                .exceptionHandling()
//                                .accessDeniedHandler(((request, response, accessDeniedException) -> {
//                                    buildAccesDeniedResponse(request, response);
//                                }))
//                                .authenticationEntryPoint(((request, response, authException) -> {
//                                    buildAuthenticationEntryPointResponse(request, response);
//                                }));
//                    } catch (Exception e) {
//                        throw new RuntimeException(e);
//                    }
//                })
//                .httpBasic(Customizer.withDefaults());
//        return http.build();
//    }
//
//
//    @Bean
//    public JsonObjectAuthenticationFilter authenticationFilter() {
//        JsonObjectAuthenticationFilter jsonFilter = new JsonObjectAuthenticationFilter();
//        jsonFilter.setAuthenticationSuccessHandler(successHandler);
//        jsonFilter.setAuthenticationFailureHandler(failureHandler);
//        jsonFilter.setAuthenticationManager(authenticationManager);
//        return jsonFilter;
//    }
//
//
//    @SneakyThrows
//    private void buildAccesDeniedResponse(HttpServletRequest request, HttpServletResponse response) {
//        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//        prepareBasicResponse(request, response, resourceCreator.of("access.denied.message"));
//    }
//
//
//    @SneakyThrows
//    private void buildAuthenticationEntryPointResponse(HttpServletRequest request, HttpServletResponse response) {
//        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        prepareBasicResponse(request, response, resourceCreator.of("unauthorized.message"));
//    }
//
//    private void prepareBasicResponse(HttpServletRequest request, HttpServletResponse response, String accessDeniedMessage) throws URISyntaxException, IOException {
//        AuthResponseDto authResponseDto = new AuthResponseDto();
//        authResponseDto.setMessage(accessDeniedMessage);
//        String jsonObject = new ObjectMapper().writeValueAsString(authResponseDto);
//        response.getWriter().write(jsonObject);
//    }


}
