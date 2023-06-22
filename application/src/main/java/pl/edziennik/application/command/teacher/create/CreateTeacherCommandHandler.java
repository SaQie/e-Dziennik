package pl.edziennik.application.command.teacher.create;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.application.common.dispatcher.command.ICommandHandler;
import pl.edziennik.application.events.event.UserAccountCreatedEvent;
import pl.edziennik.common.valueobject.Password;
import pl.edziennik.common.valueobject.PersonInformation;
import pl.edziennik.common.valueobject.id.TeacherId;
import pl.edziennik.domain.address.Address;
import pl.edziennik.domain.role.Role;
import pl.edziennik.domain.school.School;
import pl.edziennik.domain.teacher.Teacher;
import pl.edziennik.domain.user.User;
import pl.edziennik.infrastructure.repository.role.RoleCommandRepository;
import pl.edziennik.infrastructure.repository.school.SchoolCommandRepository;
import pl.edziennik.infrastructure.repository.teacher.TeacherCommandRepository;

@Component
@AllArgsConstructor
class CreateTeacherCommandHandler implements ICommandHandler<CreateTeacherCommand, OperationResult> {

    private final TeacherCommandRepository teacherCommandRepository;
    private final SchoolCommandRepository schoolCommandRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleCommandRepository roleCommandRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    @CacheEvict(allEntries = true, value = "teachers")
    public OperationResult handle(CreateTeacherCommand command) {
        School school = schoolCommandRepository.getReferenceById(command.schoolId());
        User user = createUser(command);

        PersonInformation personInformation = PersonInformation.of(command.firstName(), command.lastName(), command.phoneNumber());
        Address address = Address.of(command.address(), command.city(), command.postalCode());

        Teacher teacher = Teacher.of(user, school, personInformation, address);

        TeacherId teacherId = teacherCommandRepository.save(teacher).getTeacherId();

        eventPublisher.publishEvent(new UserAccountCreatedEvent(user.getUserId()));

        return OperationResult.success(teacherId);
    }

    private User createUser(CreateTeacherCommand command) {
        Role role = roleCommandRepository.getByName(Role.RoleConst.ROLE_TEACHER.roleName());
        Password encodedPassword = Password.of(passwordEncoder.encode(command.password().value()));

        return User.of(command.username(), encodedPassword, command.email(), command.pesel(), role);
    }
}
