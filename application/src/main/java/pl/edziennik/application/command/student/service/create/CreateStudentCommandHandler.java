package pl.edziennik.application.command.student.service.create;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.command.ICommandHandler;
import pl.edziennik.application.common.mapper.StudentMapper;
import pl.edziennik.domain.role.Role;
import pl.edziennik.domain.school.School;
import pl.edziennik.domain.schoolclass.SchoolClass;
import pl.edziennik.domain.student.Student;
import pl.edziennik.domain.student.StudentId;
import pl.edziennik.domain.user.User;
import pl.edziennik.infrastructure.command.student.StudentCommandRepository;
import pl.edziennik.infrastructure.query.role.RoleQueryRepository;
import pl.edziennik.infrastructure.query.school.SchoolQueryRepository;
import pl.edziennik.infrastructure.query.schoolclass.SchoolClassQueryRepository;

@Component
@AllArgsConstructor
class CreateStudentCommandHandler implements ICommandHandler<CreateStudentCommand, StudentId> {

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
