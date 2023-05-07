package pl.edziennik.eDziennik.command.student.service.create;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.edziennik.eDziennik.domain.role.domain.Role;
import pl.edziennik.eDziennik.domain.school.domain.School;
import pl.edziennik.eDziennik.domain.schoolclass.domain.SchoolClass;
import pl.edziennik.eDziennik.domain.student.domain.Student;
import pl.edziennik.eDziennik.domain.student.domain.wrapper.StudentId;
import pl.edziennik.eDziennik.domain.student.dto.mapper.StudentMapper;
import pl.edziennik.eDziennik.domain.user.domain.User;
import pl.edziennik.eDziennik.infrastructure.repository.command.student.StudentCommandRepository;
import pl.edziennik.eDziennik.infrastructure.repository.query.role.RoleQueryRepository;
import pl.edziennik.eDziennik.infrastructure.repository.query.school.SchoolQueryRepository;
import pl.edziennik.eDziennik.infrastructure.repository.query.schoolclass.SchoolClassQueryRepository;
import pl.edziennik.eDziennik.infrastructure.spring.command.ICommandHandler;

@Component
@AllArgsConstructor
public class CreateStudentCommandHandler implements ICommandHandler<CreateStudentCommand, StudentId> {

    private final SchoolClassQueryRepository schoolClassQueryRepository;
    private final SchoolQueryRepository schoolQueryRepository;
    private final StudentCommandRepository studentCommandRepository;
    private final RoleQueryRepository roleQueryRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public StudentId handle(CreateStudentCommand command) {
        Student student = StudentMapper.toEntity(command);

        SchoolClass schoolClass = schoolClassQueryRepository.getReferenceById(command.schoolClassId());
        School school = schoolQueryRepository.getReferenceById(command.schoolId());

        User user = createUser(command);

        student.setSchoolClass(schoolClass);
        student.setSchool(school);
        student.setUser(user);

        return studentCommandRepository.save(student).getStudentId();
    }


    private User createUser(CreateStudentCommand command) {
        User user = new User(command.username(), command.password(), command.email());
        Role role = roleQueryRepository.getByName(Role.RoleConst.ROLE_STUDENT.name());

        user.setRole(role);
        user.setPassword(passwordEncoder.encode(command.password()));

        return user;
    }
}
