package pl.edziennik.application.command.user.changeuserdata;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.IBaseValidator;
import pl.edziennik.application.common.dispatcher.ValidationErrorBuilder;
import pl.edziennik.infrastructure.repository.user.UserCommandRepository;
import pl.edziennik.infrastructure.validator.errorcode.ErrorCode;

@Component
@AllArgsConstructor
class ChangeUserDataCommandValidator implements IBaseValidator<ChangeUserDataCommand> {


    public static final String MESSAGE_KEY_USER_ALREADY_EXISTS_BY_EMAIL = "user.already.exists.by.email";
    public static final String MESSAGE_KEY_USER_ALREADY_EXISTS_BY_USERNAME = "user.already.exists";
    private final UserCommandRepository userCommandRepository;


    @Override
    public void validate(ChangeUserDataCommand command, ValidationErrorBuilder errorBuilder) {
        checkUserExists(command, errorBuilder);
        checkUserWithGivenEmailAlreadyExists(command, errorBuilder);
        checkUserWithGivenUsernameAlreadyExists(command, errorBuilder);
    }

    /**
     * Check if given user exists
     */
    private void checkUserExists(ChangeUserDataCommand command, ValidationErrorBuilder errorBuilder) {
        userCommandRepository.findById(command.userId())
                .orElseGet(() -> {
                    errorBuilder.addNotFoundError(ChangeUserDataCommand.USER_ID);
                    return null;
                });

        errorBuilder.flush();
    }

    /**
     * Check if user with given email already exists
     */
    private void checkUserWithGivenEmailAlreadyExists(ChangeUserDataCommand command, ValidationErrorBuilder errorBuilder) {
        if (userCommandRepository.existsByEmail(command.email())) {
            if (userCommandRepository.existsByEmail(command.email())) {
                errorBuilder.addError(
                        ChangeUserDataCommand.EMAIL,
                        MESSAGE_KEY_USER_ALREADY_EXISTS_BY_EMAIL,
                        ErrorCode.OBJECT_ALREADY_EXISTS,
                        command.email());
            }
        }
    }

    /**
     * Check if user with given username already exists
     */
    private void checkUserWithGivenUsernameAlreadyExists(ChangeUserDataCommand command, ValidationErrorBuilder errorBuilder) {
        if (userCommandRepository.existsByUsername(command.username())) {
            errorBuilder.addError(
                    ChangeUserDataCommand.USERNAME,
                    MESSAGE_KEY_USER_ALREADY_EXISTS_BY_USERNAME,
                    ErrorCode.OBJECT_ALREADY_EXISTS,
                    command.username());
        }
    }
}
