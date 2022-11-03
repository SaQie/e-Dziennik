package pl.edziennik.eDziennik.server.config.security.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.eDziennik.server.basics.BaseDao;
import pl.edziennik.eDziennik.server.student.dao.StudentDao;
import pl.edziennik.eDziennik.server.student.domain.Student;
import pl.edziennik.eDziennik.server.student.services.StudentService;
import pl.edziennik.eDziennik.server.teacher.dao.TeacherDao;
import pl.edziennik.eDziennik.server.teacher.domain.Teacher;
import pl.edziennik.eDziennik.server.teacher.services.TeacherService;
import pl.edziennik.eDziennik.server.utils.JwtUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@Component
@Slf4j
public class AuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private JwtUtils jwtUtils;
    private final int expTime;
    private final String secret;

    public AuthSuccessHandler(@Value("${jwt.expiration}") int expTime, @Value("${jwt.secret}") String secret) {
        this.expTime = expTime;
        this.secret = secret;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserDetails principal = jwtUtils.getPrincipal(authentication);
        if(!teacherService.updateTeacherLastLoginDate(principal.getUsername())){
            studentService.updateStudentLastLoginDate(principal.getUsername());
        }
        String token = jwtUtils.generateJwtToken(principal);
        String refreshToken = jwtUtils.generateRefreshToken(principal);
        setRequiredHeadersAndPrintTokenToUser(response, token, refreshToken);
    }

    private void setRequiredHeadersAndPrintTokenToUser(HttpServletResponse response, String token, String refreshToken) throws IOException {
        response.addHeader("Authorization", "Bearer " + token);
        response.addHeader("RefreshToken", refreshToken);
        response.addHeader("Content-Type", "application/json");
        response.getWriter().write("{\"token\": \""+ token +"\"}");
        response.getWriter().write("{\"refresh_token\": \""+ refreshToken +"\"}");
    }
}
