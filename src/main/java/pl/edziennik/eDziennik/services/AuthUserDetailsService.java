package pl.edziennik.eDziennik.services;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.authentication.StudentUserDetails;
import pl.edziennik.eDziennik.authentication.TeacherUserDetails;
import pl.edziennik.eDziennik.entities.Student;
import pl.edziennik.eDziennik.entities.Teacher;
import pl.edziennik.eDziennik.repositories.StudentRepository;
import pl.edziennik.eDziennik.repositories.TeacherRepository;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
public class AuthUserDetailsService implements UserDetailsService {

    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Teacher teacher = teacherRepository.findByUsername(username);
        if (teacher != null){
            return new TeacherUserDetails(teacher);
        }
        Student student = studentRepository.findByUsername(username);
        if (student != null){
            return new StudentUserDetails(student);
        }
        throw new EntityNotFoundException("User with name " + username + "could not be found.");
    }
}
