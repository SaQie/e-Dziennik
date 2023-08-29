package pl.edziennik.application.command.user.changepassword;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.IBaseValidator;
import pl.edziennik.application.common.dispatcher.ValidationErrorBuilder;
import pl.edziennik.common.valueobject.vo.Password;
import pl.edziennik.domain.user.User;
import pl.edziennik.infrastructure.repository.user.UserCommandRepository;
import pl.edziennik.infrastructure.validator.errorcode.ErrorCode;

@Component
@AllArgsConstructor
class ChangePasswordCommandValidator implements IBaseValidator<ChangePasswordCommand> {

    public static final String MESSAGE_KEY_OLD_PASSWORD_IS_INCORRECT = "old.password.is.incorrect";
    private final PasswordEncoder passwordEncoder;
    private final UserCommandRepository userCommandRepository;

    @Override
    public void validate(ChangePasswordCommand command, ValidationErrorBuilder errorBuilder) {
        checkUserExists(command, errorBuilder);
        checkPassword(command, errorBuilder);
    }

    /**
     * Check if old password is equal to actual password
     */
    private void checkPassword(ChangePasswordCommand command, ValidationErrorBuilder errorBuilder) {
        User user = userCommandRepository.getUserByUserId(command.userId());
        Password password = user.password();

        boolean isOldPasswordCorrectly = passwordEncoder.matches(command.oldPassword().value(), password.value());

        if (!isOldPasswordCorrectly) {
            errorBuilder.addError(
                    ChangePasswordCommand.OLD_PASSWORD,
                    MESSAGE_KEY_OLD_PASSWORD_IS_INCORRECT,
                    ErrorCode.PASSWORD_INCORRECT
            );
        }

    }

    /**
     * Check if given user exists
     */
    private void checkUserExists(ChangePasswordCommand command, ValidationErrorBuilder errorBuilder) {
        userCommandRepository.findById(command.userId())
                .orElseGet(() -> {
                    errorBuilder.addNotFoundError(ChangePasswordCommand.USER_ID);
                    return null;
                });

        errorBuilder.flush();
    }
}
