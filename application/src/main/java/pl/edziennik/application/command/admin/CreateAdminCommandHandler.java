package pl.edziennik.application.command.admin;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.application.common.dispatcher.command.ICommandHandler;
import pl.edziennik.common.valueobject.Password;
import pl.edziennik.common.valueobject.id.AdminId;
import pl.edziennik.domain.admin.Admin;
import pl.edziennik.domain.role.Role;
import pl.edziennik.domain.user.User;
import pl.edziennik.infrastructure.repositories.admin.AdminCommandRepository;
import pl.edziennik.infrastructure.repositories.role.RoleCommandRepository;

@Component
@AllArgsConstructor
class CreateAdminCommandHandler implements ICommandHandler<CreateAdminCommand, OperationResult> {

    private final AdminCommandRepository adminCommandRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleCommandRepository roleCommandRepository;

    @Override
    public OperationResult handle(CreateAdminCommand command) {
        Role role = roleCommandRepository.getByName(Role.RoleConst.ROLE_ADMIN.roleName());

        Password password = Password.of(passwordEncoder.encode(command.password().value()));
        User user = User.of(command.username(), password, command.email(), role);

        AdminId adminId = adminCommandRepository.save(Admin.of(user)).getAdminId();
        return OperationResult.success(adminId);
    }
}
