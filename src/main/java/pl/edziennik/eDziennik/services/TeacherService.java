package pl.edziennik.eDziennik.services;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.dto.AuthCredentials;
import pl.edziennik.eDziennik.entities.Role;
import pl.edziennik.eDziennik.entities.School;
import pl.edziennik.eDziennik.entities.Student;
import pl.edziennik.eDziennik.entities.Teacher;
import pl.edziennik.eDziennik.repositories.SchoolRepository;
import pl.edziennik.eDziennik.repositories.TeacherRepository;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class TeacherService {

    private final TeacherRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final SchoolRepository schoolRepository;

    public void register(AuthCredentials authCredentials){
        Teacher teacher = new Teacher();
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(Role.ROLE_ADMIN);
        teacher.setRoles(roleSet);
        teacher.setPESEL("asdads");
        teacher.setAdress("asd");
        teacher.setPostalCode("123");
        teacher.setCity("asd");
        teacher.setLastName("asdad");
        teacher.setFirstName("asdasd");
        teacher.setUsername(authCredentials.getUsername());
        teacher.setPassword(passwordEncoder.encode(authCredentials.getPassword()));
        teacher.setPhoneNumber("asd");
        teacher.setSchool(schoolRepository.findById(1L).get());
        repository.save(teacher);
    }

}
