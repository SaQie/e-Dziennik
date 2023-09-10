package pl.edziennik.application.command.user.changepassword;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.CommandHandler;
import pl.edziennik.common.valueobject.vo.Password;
import pl.edziennik.domain.user.User;
import pl.edziennik.infrastructure.repository.user.UserCommandRepository;

@Component
@AllArgsConstructor
class ChangePasswordCommandHandler implements CommandHandler<ChangePasswordCommand> {

    private final UserCommandRepository userCommandRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void handle(ChangePasswordCommand command) {
        User user = userCommandRepository.getUserByUserId(command.userId());

        String encodedPassword = passwordEncoder.encode(command.newPassword().value());
        user.changePassword(Password.of(encodedPassword));

        userCommandRepository.save(user);
    }
}
