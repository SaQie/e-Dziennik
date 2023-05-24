package pl.edziennik.application.command.parent.create;

import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.application.common.dispatcher.command.ICommandHandler;
import pl.edziennik.application.events.event.UserAccountCreatedEvent;
import pl.edziennik.common.valueobject.Password;
import pl.edziennik.common.valueobject.PersonInformation;
import pl.edziennik.common.valueobject.id.ParentId;
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
    public OperationResult handle(CreateParentCommand command) {
        User user = createUser(command);

        PersonInformation personInformation = PersonInformation.of(command.firstName(), command.lastName(), command.phoneNumber(), command.pesel());
        Address address = Address.of(command.address(), command.city(), command.postalCode());

        Parent parent = Parent.of(user, personInformation, address);

        ParentId parentId = parentCommandRepository.save(parent).getParentId();

        eventPublisher.publishEvent(new UserAccountCreatedEvent(user.getUserId()));

        return OperationResult.success(parentId);

    }

    private User createUser(CreateParentCommand command) {
        Role role = roleCommandRepository.getByName(Role.RoleConst.ROLE_PARENT.roleName());

        Password encodedPassword = Password.of(passwordEncoder.encode(command.password().value()));

        return User.of(command.username(), encodedPassword, command.email(), role);
    }
}
