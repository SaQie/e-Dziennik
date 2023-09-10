package pl.edziennik.application.command.student.create;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.application.common.dispatcher.CommandHandler;
import pl.edziennik.application.events.event.StudentAccountCreatedEvent;
import pl.edziennik.application.events.event.UserAccountCreatedEvent;
import pl.edziennik.common.valueobject.id.RoleId;
import pl.edziennik.common.valueobject.id.StudentId;
import pl.edziennik.common.valueobject.vo.Password;
import pl.edziennik.common.valueobject.vo.PersonInformation;
import pl.edziennik.domain.address.Address;
import pl.edziennik.domain.role.Role;
import pl.edziennik.domain.school.School;
import pl.edziennik.domain.schoolclass.SchoolClass;
import pl.edziennik.domain.student.Student;
import pl.edziennik.domain.user.User;
import pl.edziennik.infrastructure.repository.role.RoleCommandRepository;
import pl.edziennik.infrastructure.repository.school.SchoolCommandRepository;
import pl.edziennik.infrastructure.repository.schoolclass.SchoolClassCommandRepository;
import pl.edziennik.infrastructure.repository.student.StudentCommandRepository;

@Component
@AllArgsConstructor
class CreateStudentCommandHandler implements CommandHandler<CreateStudentCommand> {

    private final SchoolClassCommandRepository schoolClassCommandRepository;
    private final SchoolCommandRepository schoolCommandRepository;
    private final StudentCommandRepository studentCommandRepository;
    private final RoleCommandRepository roleCommandRepository;
    private final PasswordEncoder passwordEncoder;

    private final ApplicationEventPublisher eventPublisher;


    @Override
    @Transactional
    @CacheEvict(allEntries = true, value = "students")
    public void handle(CreateStudentCommand command) {
        SchoolClass schoolClass = schoolClassCommandRepository.getBySchoolClassId(command.schoolClassId());
        School school = schoolClass.school();

        User user = createUser(command);
        PersonInformation personInformation = createPersonInformation(command);
        Address address = createAddress(command);

        Student student = Student.builder()
                .user(user)
                .school(school)
                .schoolClass(schoolClass)
                .personInformation(personInformation)
                .address(address)
                .build();

        StudentId studentId = studentCommandRepository.save(student).studentId();

        UserAccountCreatedEvent userAccountCreatedEvent = new UserAccountCreatedEvent(user.userId());
        StudentAccountCreatedEvent studentAccountCreatedEvent = new StudentAccountCreatedEvent(studentId, command.schoolClassId());

        eventPublisher.publishEvent(userAccountCreatedEvent);
        eventPublisher.publishEvent(studentAccountCreatedEvent);
    }


    private User createUser(CreateStudentCommand command) {
        Role role = roleCommandRepository.getByRoleId(RoleId.PredefinedRow.ROLE_STUDENT);
        Password password = Password.of(passwordEncoder.encode(command.password().value()));

        return User.builder()
                .role(role)
                .pesel(command.pesel())
                .username(command.username())
                .password(password)
                .email(command.email())
                .build();
    }

    private PersonInformation createPersonInformation(CreateStudentCommand command) {
        return PersonInformation.builder()
                .firstName(command.firstName())
                .lastName(command.lastName())
                .phoneNumber(command.phoneNumber())
                .build();
    }


    private Address createAddress(CreateStudentCommand command) {
        return Address.builder()
                .address(command.address())
                .city(command.city())
                .postalCode(command.postalCode())
                .build();
    }
}
