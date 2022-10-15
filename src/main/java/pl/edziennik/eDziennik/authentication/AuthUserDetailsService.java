package pl.edziennik.eDziennik.authentication;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.server.student.StudentRepository;
import pl.edziennik.eDziennik.server.student.domain.Student;
import pl.edziennik.eDziennik.server.teacher.TeacherDao;
import pl.edziennik.eDziennik.server.teacher.domain.Teacher;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
public class AuthUserDetailsService implements UserDetailsService {

    private final TeacherDao dao;
    private final StudentRepository studentRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Teacher teacher = dao.findByUsername(username);
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
