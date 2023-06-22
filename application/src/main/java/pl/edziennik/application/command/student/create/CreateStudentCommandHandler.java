package pl.edziennik.application.command.student.create;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.application.common.dispatcher.command.ICommandHandler;
import pl.edziennik.application.events.event.StudentAccountCreatedEvent;
import pl.edziennik.application.events.event.UserAccountCreatedEvent;
import pl.edziennik.common.valueobject.Password;
import pl.edziennik.common.valueobject.PersonInformation;
import pl.edziennik.common.valueobject.id.StudentId;
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
class CreateStudentCommandHandler implements ICommandHandler<CreateStudentCommand, OperationResult> {

    private final SchoolClassCommandRepository schoolClassCommandRepository;
    private final SchoolCommandRepository schoolCommandRepository;
    private final StudentCommandRepository studentCommandRepository;
    private final RoleCommandRepository roleCommandRepository;
    private final PasswordEncoder passwordEncoder;

    private final ApplicationEventPublisher eventPublisher;


    @Override
    @Transactional
    @CacheEvict(allEntries = true, value = "students")
    public OperationResult handle(CreateStudentCommand command) {
        SchoolClass schoolClass = schoolClassCommandRepository.getBySchoolClassId(command.schoolClassId());
        School school = schoolCommandRepository.getBySchoolId(command.schoolId());

        User user = createUser(command);
        PersonInformation personInformation = PersonInformation.of(command.firstName(), command.lastName(), command.phoneNumber());
        Address address = Address.of(command.address(), command.city(), command.postalCode());

        Student student = Student.of(user, school, schoolClass, personInformation, address);
        StudentId studentId = studentCommandRepository.save(student).getStudentId();

        eventPublisher.publishEvent(new UserAccountCreatedEvent(user.getUserId()));
        eventPublisher.publishEvent(new StudentAccountCreatedEvent(studentId, command.schoolClassId()));

        return OperationResult.success(studentId);
    }


    private User createUser(CreateStudentCommand command) {
        Role role = roleCommandRepository.getByName(Role.RoleConst.ROLE_STUDENT.roleName());
        Password encodedPassword = Password.of(passwordEncoder.encode(command.password().value()));

        return User.of(command.username(), encodedPassword, command.email(), command.pesel(),role);
    }
}
