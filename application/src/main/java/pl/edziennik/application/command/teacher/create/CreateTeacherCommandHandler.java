package pl.edziennik.application.command.teacher.create;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.application.common.dispatcher.ICommandHandler;
import pl.edziennik.application.events.event.UserAccountCreatedEvent;
import pl.edziennik.common.valueobject.vo.Password;
import pl.edziennik.common.valueobject.vo.PersonInformation;
import pl.edziennik.common.valueobject.id.RoleId;
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
        PersonInformation personInformation = createPersonInformation(command);
        Address address = createAddress(command);

        Teacher teacher = Teacher.builder()
                .user(user)
                .school(school)
                .personInformation(personInformation)
                .address(address)
                .build();

        TeacherId teacherId = teacherCommandRepository.save(teacher).teacherId();

        UserAccountCreatedEvent accountCreatedEvent = new UserAccountCreatedEvent(user.userId());
        eventPublisher.publishEvent(accountCreatedEvent);

        return OperationResult.success(teacherId);
    }

    private User createUser(CreateTeacherCommand command) {
        Role role = roleCommandRepository.getByRoleId(RoleId.PredefinedRow.ROLE_TEACHER);
        Password password = Password.of(passwordEncoder.encode(command.password().value()));

        return User.builder()
                .role(role)
                .pesel(command.pesel())
                .username(command.username())
                .password(password)
                .email(command.email())
                .build();
    }

    private PersonInformation createPersonInformation(CreateTeacherCommand command) {
        return PersonInformation.builder()
                .firstName(command.firstName())
                .lastName(command.lastName())
                .phoneNumber(command.phoneNumber())
                .build();
    }


    private Address createAddress(CreateTeacherCommand command) {
        return Address.builder()
                .address(command.address())
                .city(command.city())
                .postalCode(command.postalCode())
                .build();
    }
}
