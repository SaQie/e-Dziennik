package pl.edziennik.eDziennik.server.student;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.dto.AuthCredentials;
import pl.edziennik.eDziennik.server.repositories.StudentRepository;

@Service
@AllArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

    public void registerStudent(AuthCredentials authCredentials){
        Student student = new Student();
        student.setUsername(authCredentials.getUsername());
        student.setPassword(passwordEncoder.encode(authCredentials.getPassword()));
        student.setCity("asd");
        student.setAdress("asd");
        student.setFirstName("asdasd");
        student.setLastName("asdasd");
        studentRepository.save(student);
    }

}
