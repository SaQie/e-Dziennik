package pl.edziennik.application.command.user.createuser;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edziennik.application.common.dispatcher.command.ICommandHandler;
import pl.edziennik.domain.role.Role;
import pl.edziennik.domain.user.User;
import pl.edziennik.domain.user.UserId;
import pl.edziennik.infrastructure.command.user.UserCommandRepository;
import pl.edziennik.infrastructure.query.role.RoleQueryRepository;

@Service
@AllArgsConstructor
public class CreateUserCommandHandler implements ICommandHandler<CreateUserCommand, UserId> {

    private final UserCommandRepository userRepository;
    private final RoleQueryRepository roleQueryRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserId handle(CreateUserCommand command) {
        User user = new User(command.username(), command.password(), command.email());
        Role role = roleQueryRepository.getByName(command.role());

        user.setRole(role);
        user.setPassword(passwordEncoder.encode(command.password()));

        return userRepository.save(user).getUserId();
    }
}
