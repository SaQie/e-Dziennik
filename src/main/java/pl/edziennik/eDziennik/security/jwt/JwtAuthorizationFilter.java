package pl.edziennik.eDziennik.security.jwt;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import pl.edziennik.eDziennik.utils.JwtUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final UserDetailsService userDetailsService;
    private JwtUtils jwtUtils;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JwtUtils jwtUtils) {
        super(authenticationManager);
        this.userDetailsService = userDetailsService;
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        UsernamePasswordAuthenticationToken auth = getAuthentication(request, response);
        if (auth == null){
            chain.doFilter(request,response);
            return;
        }
        SecurityContextHolder.getContext().setAuthentication(auth);
        chain.doFilter(request,response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String token = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (jwtUtils.isTokenNotExist(token)) return null;
            String login = jwtUtils.getUsernameFromToken(token);
            Collection<SimpleGrantedAuthority> authorities = jwtUtils.getRolesFromTokenIfNeeded(token);
            if (login == null) return null;
            UserDetails userDetails = userDetailsService.loadUserByUsername(login);
            return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, authorities);
        }catch (Exception e){
            response.setHeader("error", e.getMessage());
            response.setContentType("application/json");
            response.getWriter().write(e.getMessage());
            return null;
        }
    }

}
