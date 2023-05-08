package pl.edziennik.application.command.student.create;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.application.common.dispatcher.command.ICommandHandler;
import pl.edziennik.application.common.mapper.StudentMapper;
import pl.edziennik.domain.role.Role;
import pl.edziennik.domain.school.School;
import pl.edziennik.domain.schoolclass.SchoolClass;
import pl.edziennik.domain.student.Student;
import pl.edziennik.domain.student.StudentId;
import pl.edziennik.domain.user.User;
import pl.edziennik.infrastructure.command.role.RoleCommandRepository;
import pl.edziennik.infrastructure.command.school.SchoolCommandRepository;
import pl.edziennik.infrastructure.command.student.StudentCommandRepository;
import pl.edziennik.infrastructure.query.schoolclass.SchoolClassQueryRepository;

@Component
@AllArgsConstructor
class CreateStudentCommandHandler implements ICommandHandler<CreateStudentCommand, OperationResult> {

    private final SchoolClassQueryRepository schoolClassCommandRepository;
    private final SchoolCommandRepository schoolCommandRepository;
    private final StudentCommandRepository studentCommandRepository;
    private final RoleCommandRepository roleCommandRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    @Transactional
    public OperationResult handle(CreateStudentCommand command) {
        Student student = StudentMapper.toEntity(command);

        SchoolClass schoolClass = schoolClassCommandRepository.getReferenceById(command.schoolClassId());
        School school = schoolCommandRepository.getReferenceById(command.schoolId());

        User user = createUser(command);

        student.setSchoolClass(schoolClass);
        student.setSchool(school);
        student.setUser(user);

        StudentId studentId = studentCommandRepository.save(student).getStudentId();

        return OperationResult.of(studentId);
    }


    private User createUser(CreateStudentCommand command) {
        User user = new User(command.username(), command.password(), command.email());
        Role role = roleCommandRepository.getByName(Role.RoleConst.ROLE_STUDENT.name());

        user.setRole(role);
        user.setPassword(passwordEncoder.encode(command.password()));

        return user;
    }
}
