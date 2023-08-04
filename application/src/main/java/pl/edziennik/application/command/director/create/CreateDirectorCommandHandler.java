package pl.edziennik.application.command.director.create;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.application.common.dispatcher.command.ICommandHandler;
import pl.edziennik.common.valueobject.Password;
import pl.edziennik.common.valueobject.PersonInformation;
import pl.edziennik.common.valueobject.id.DirectorId;
import pl.edziennik.common.valueobject.id.RoleId;
import pl.edziennik.domain.address.Address;
import pl.edziennik.domain.director.Director;
import pl.edziennik.domain.role.Role;
import pl.edziennik.domain.school.School;
import pl.edziennik.domain.user.User;
import pl.edziennik.infrastructure.repository.director.DirectorCommandRepository;
import pl.edziennik.infrastructure.repository.role.RoleCommandRepository;
import pl.edziennik.infrastructure.repository.school.SchoolCommandRepository;

@Component
@AllArgsConstructor
class CreateDirectorCommandHandler implements ICommandHandler<CreateDirectorCommand, OperationResult> {

    private final DirectorCommandRepository directorCommandRepository;
    private final SchoolCommandRepository schoolCommandRepository;
    private final RoleCommandRepository roleCommandRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    @CacheEvict(allEntries = true, value = "directors")
    public OperationResult handle(CreateDirectorCommand command) {
        School school = schoolCommandRepository.getReferenceById(command.schoolId());

        User user = createUser(command);
        PersonInformation personInformation = createPersonInformation(command);
        Address address = createAddress(command);

        Director director = Director.builder()
                .address(address)
                .personInformation(personInformation)
                .school(school)
                .user(user)
                .build();

        DirectorId directorId = directorCommandRepository.save(director).directorId();

        return OperationResult.success(directorId);
    }


    private User createUser(CreateDirectorCommand command) {
        Role role = roleCommandRepository.getByRoleId(RoleId.PredefinedRow.ROLE_DIRECTOR);
        Password password = Password.of(passwordEncoder.encode(command.password().value()));

        return User.builder()
                .username(command.username())
                .password(password)
                .email(command.email())
                .pesel(command.pesel())
                .role(role)
                .build();
    }

    private PersonInformation createPersonInformation(CreateDirectorCommand command) {
        return PersonInformation.builder()
                .firstName(command.firstName())
                .lastName(command.lastName())
                .phoneNumber(command.phoneNumber())
                .build();
    }

    private Address createAddress(CreateDirectorCommand command) {
        return Address.builder()
                .address(command.address())
                .city(command.city())
                .postalCode(command.postalCode())
                .build();
    }

}
