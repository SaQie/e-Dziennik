package pl.edziennik.eDziennik.command.user.createuser;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.domain.role.domain.Role;
import pl.edziennik.eDziennik.domain.user.domain.User;
import pl.edziennik.eDziennik.domain.user.domain.wrapper.UserId;
import pl.edziennik.eDziennik.infrastructure.repository.command.user.UserCommandRepository;
import pl.edziennik.eDziennik.infrastructure.repository.query.role.RoleQueryRepository;
import pl.edziennik.eDziennik.infrastructure.spring.command.ICommandHandler;

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
