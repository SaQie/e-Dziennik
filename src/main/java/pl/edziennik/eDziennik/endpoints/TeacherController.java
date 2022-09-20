package pl.edziennik.eDziennik.endpoints;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.edziennik.eDziennik.dto.AuthCredentials;
import pl.edziennik.eDziennik.services.StudentService;
import pl.edziennik.eDziennik.services.TeacherService;

@RestController
@AllArgsConstructor
public class TeacherController {

    private final TeacherService service;
    private final PasswordEncoder passwordEncoder;
    private final StudentService studentService;


    @GetMapping("/teacher/teacher")
    public String test(){
        return "Teacher endpoint";
    }

    @GetMapping("/teacher/admin")
    public String test2(){
        return "admin endpoint";
    }

    @GetMapping("/teacher/moderator")
    public String test3(){
        return "moderator endpoint";
    }

    @PostMapping("/login")
    public void login(@RequestBody AuthCredentials authCredentials){

    }

    @PostMapping("/register")
    public String register(@RequestBody AuthCredentials authCredentials){
        service.register(authCredentials);
        return null;
    }

    @GetMapping("/test")
    public String test4(){
        return "heh";
    }

    @PostMapping("/register/student")
    public String register2(@RequestBody AuthCredentials authCredentials){
        studentService.registerStudent(authCredentials);
        return null;
    }

}
