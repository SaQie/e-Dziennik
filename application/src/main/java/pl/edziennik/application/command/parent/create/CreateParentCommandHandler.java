package pl.edziennik.application.command.parent.create;

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
import pl.edziennik.common.valueobject.id.ParentId;
import pl.edziennik.common.valueobject.id.RoleId;
import pl.edziennik.domain.address.Address;
import pl.edziennik.domain.parent.Parent;
import pl.edziennik.domain.role.Role;
import pl.edziennik.domain.user.User;
import pl.edziennik.infrastructure.repository.parent.ParentCommandRepository;
import pl.edziennik.infrastructure.repository.role.RoleCommandRepository;

@Component
@AllArgsConstructor
class CreateParentCommandHandler implements ICommandHandler<CreateParentCommand, OperationResult> {

    private final ParentCommandRepository parentCommandRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleCommandRepository roleCommandRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    @CacheEvict(allEntries = true, value = "parents")
    public OperationResult handle(CreateParentCommand command) {
        User user = createUser(command);
        PersonInformation personInformation = createPersonInformation(command);
        Address address = createAddress(command);

        Parent parent = Parent.builder()
                .user(user)
                .personInformation(personInformation)
                .address(address)
                .build();

        ParentId parentId = parentCommandRepository.save(parent).parentId();

        UserAccountCreatedEvent event = new UserAccountCreatedEvent(user.userId());
        eventPublisher.publishEvent(event);

        return OperationResult.success(parentId);

    }

    private User createUser(CreateParentCommand command) {
        Role role = roleCommandRepository.getByRoleId(RoleId.PredefinedRow.ROLE_PARENT);

        Password password = Password.of(passwordEncoder.encode(command.password().value()));

        return User.builder()
                .username(command.username())
                .password(password)
                .email(command.email())
                .pesel(command.pesel())
                .role(role)
                .build();
    }

    private PersonInformation createPersonInformation(CreateParentCommand command) {
        return PersonInformation.builder()
                .firstName(command.firstName())
                .lastName(command.lastName())
                .phoneNumber(command.phoneNumber())
                .build();
    }

    private Address createAddress(CreateParentCommand command) {
        return Address.builder()
                .address(command.address())
                .city(command.city())
                .postalCode(command.postalCode())
                .build();
    }
}
