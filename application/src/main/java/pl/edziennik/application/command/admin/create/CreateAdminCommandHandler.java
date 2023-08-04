package pl.edziennik.application.command.admin.create;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.application.common.dispatcher.command.ICommandHandler;
import pl.edziennik.common.valueobject.Password;
import pl.edziennik.common.valueobject.id.AdminId;
import pl.edziennik.common.valueobject.id.RoleId;
import pl.edziennik.domain.admin.Admin;
import pl.edziennik.domain.role.Role;
import pl.edziennik.domain.user.User;
import pl.edziennik.infrastructure.repository.admin.AdminCommandRepository;
import pl.edziennik.infrastructure.repository.role.RoleCommandRepository;

@Component
@AllArgsConstructor
class CreateAdminCommandHandler implements ICommandHandler<CreateAdminCommand, OperationResult> {

    private final AdminCommandRepository adminCommandRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleCommandRepository roleCommandRepository;

    @Override
    public OperationResult handle(CreateAdminCommand command) {
        User user = createUser(command);

        user.activate();

        AdminId adminId = adminCommandRepository.save(Admin.of(user)).adminId();

        return OperationResult.success(adminId);
    }

    private User createUser(CreateAdminCommand command) {
        Role role = roleCommandRepository.getByRoleId(RoleId.PredefinedRow.ROLE_ADMIN);

        Password password = Password.of(passwordEncoder.encode(command.password().value()));

        return User.builder()
                .username(command.username())
                .password(password)
                .email(command.email())
                .pesel(command.pesel())
                .role(role)
                .build();
    }
}
