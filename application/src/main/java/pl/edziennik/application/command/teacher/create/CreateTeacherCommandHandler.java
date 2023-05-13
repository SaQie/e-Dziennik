package pl.edziennik.application.command.teacher.create;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.application.common.dispatcher.command.ICommandHandler;
import pl.edziennik.common.valueobject.Password;
import pl.edziennik.common.valueobject.PersonInformation;
import pl.edziennik.common.valueobject.id.TeacherId;
import pl.edziennik.domain.address.Address;
import pl.edziennik.domain.role.Role;
import pl.edziennik.domain.school.School;
import pl.edziennik.domain.teacher.Teacher;
import pl.edziennik.domain.user.User;
import pl.edziennik.infrastructure.repositories.role.RoleCommandRepository;
import pl.edziennik.infrastructure.repositories.school.SchoolCommandRepository;
import pl.edziennik.infrastructure.repositories.teacher.TeacherCommandRepository;

@Component
@AllArgsConstructor
class CreateTeacherCommandHandler implements ICommandHandler<CreateTeacherCommand, OperationResult> {

    private final TeacherCommandRepository teacherCommandRepository;
    private final SchoolCommandRepository schoolCommandRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleCommandRepository roleCommandRepository;

    @Override
    public OperationResult handle(CreateTeacherCommand command) {
        School school = schoolCommandRepository.getReferenceById(command.schoolId());
        User user = createUser(command);

        PersonInformation personInformation = PersonInformation.of(command.firstName(), command.lastName(), command.phoneNumber(), command.pesel());
        Address address = Address.of(command.address(), command.city(), command.postalCode());

        Teacher teacher = Teacher.of(user, school, personInformation, address);

        TeacherId teacherId = teacherCommandRepository.save(teacher).getTeacherId();

        return OperationResult.success(teacherId);
    }

    private User createUser(CreateTeacherCommand command) {
        Role role = roleCommandRepository.getByName(Role.RoleConst.ROLE_STUDENT.roleName());
        Password encodedPassword = Password.of(passwordEncoder.encode(command.password().value()));

        return User.of(command.username(), encodedPassword, command.email(), role);
    }
}