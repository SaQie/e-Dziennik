package pl.edziennik.eDziennik.server.config.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.eDziennik.server.admin.domain.dto.AdminRequestApiDto;
import pl.edziennik.eDziennik.server.admin.domain.dto.AdminResponseApiDto;
import pl.edziennik.eDziennik.server.admin.services.AdminService;
import pl.edziennik.eDziennik.server.basics.ApiResponse;
import pl.edziennik.eDziennik.server.basics.BaseDao;
import pl.edziennik.eDziennik.server.config.security.AccountType;
import pl.edziennik.eDziennik.server.config.security.jwt.dto.AuthResponseDto;
import pl.edziennik.eDziennik.server.student.dao.StudentDao;
import pl.edziennik.eDziennik.server.student.domain.Student;
import pl.edziennik.eDziennik.server.student.domain.dto.StudentResponseApiDto;
import pl.edziennik.eDziennik.server.student.services.StudentService;
import pl.edziennik.eDziennik.server.teacher.dao.TeacherDao;
import pl.edziennik.eDziennik.server.teacher.domain.Teacher;
import pl.edziennik.eDziennik.server.teacher.domain.dto.TeacherResponseApiDto;
import pl.edziennik.eDziennik.server.teacher.services.TeacherService;
import pl.edziennik.eDziennik.server.utils.JwtUtils;
import pl.edziennik.eDziennik.server.utils.ResourceCreator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;

@Component
@Slf4j
public class AuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private JwtUtils jwtUtils;

    @Value("${jwt.token.prefix}")
    private String tokenPrefix;


    @SneakyThrows
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserDetails principal = jwtUtils.getPrincipal(authentication);
        StudentResponseApiDto studentDto = studentService.getStudentByUsername(principal.getUsername());

        if (studentDto != null){
            String token = jwtUtils.generateJwtToken(principal, studentDto.getId());
            String refreshToken = jwtUtils.generateRefreshToken(principal, studentDto.getId());
            studentService.updateStudentLastLoginDate(principal.getUsername());
            AuthResponseDto authResponseDto = new AuthResponseDto();
            authResponseDto.setAccountType(AccountType.STUDENT);
            authResponseDto.setUsername(studentDto.getUsername());
            authResponseDto.setToken(token);
            authResponseDto.setRefreshToken(refreshToken);
            ApiResponse<AuthResponseDto> apiResponseDto = ApiResponse.buildApiResponse(HttpMethod.POST, HttpStatus.OK, authResponseDto, new URI(request.getRequestURI()));
            setRequiredHeadersAndPrintTokenToUser(response, apiResponseDto);
            return;
        }

        TeacherResponseApiDto teacherDto = teacherService.getTeacherByUsername(principal.getUsername());

        if (teacherDto != null){
            String token = jwtUtils.generateJwtToken(principal, teacherDto.getId());
            String refreshToken = jwtUtils.generateRefreshToken(principal, teacherDto.getId());
            teacherService.updateTeacherLastLoginDate(principal.getUsername());
            AuthResponseDto authResponseDto = new AuthResponseDto();
            authResponseDto.setAccountType(AccountType.TEACHER);
            authResponseDto.setUsername(teacherDto.getUsername());
            authResponseDto.setToken(token);
            authResponseDto.setRefreshToken(refreshToken);
            ApiResponse<AuthResponseDto> apiResponseDto = ApiResponse.buildApiResponse(HttpMethod.POST, HttpStatus.OK, authResponseDto, new URI(request.getRequestURI()));
            setRequiredHeadersAndPrintTokenToUser(response, apiResponseDto);
            return;
        }

        AdminResponseApiDto adminDto = adminService.getAdminByUsername(principal.getUsername());

        if (adminDto != null){
            String token = jwtUtils.generateJwtToken(principal, adminDto.getId());
            String refreshToken = jwtUtils.generateRefreshToken(principal, adminDto.getId());
            AuthResponseDto authResponseDto = new AuthResponseDto();
            authResponseDto.setAccountType(AccountType.ADMIN);
            authResponseDto.setUsername(adminDto.getUsername());
            authResponseDto.setToken(token);
            authResponseDto.setRefreshToken(refreshToken);
            ApiResponse<AuthResponseDto> apiResponseDto = ApiResponse.buildApiResponse(HttpMethod.POST, HttpStatus.OK, authResponseDto, new URI(request.getRequestURI()));
            setRequiredHeadersAndPrintTokenToUser(response, apiResponseDto);
        }

    }

    private void setRequiredHeadersAndPrintTokenToUser(HttpServletResponse response, ApiResponse<AuthResponseDto> authResponseDto) throws IOException {
        response.addHeader("Authorization", tokenPrefix + " " + authResponseDto.getResult().getToken());
        response.addHeader("RefreshToken", authResponseDto.getResult().getRefreshToken());
        response.addHeader("Content-Type", "application/json");
        String jsonObject = new ObjectMapper().writeValueAsString(authResponseDto);
        response.getWriter().write(jsonObject);

    }
}
