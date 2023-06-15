package pl.edziennik.application.command.user.activate;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.ValidationErrorBuilder;
import pl.edziennik.application.common.dispatcher.base.IBaseValidator;
import pl.edziennik.common.valueobject.id.UserId;
import pl.edziennik.infrastructure.repository.token.ActivationTokenRepository;
import pl.edziennik.infrastructure.validator.errorcode.ErrorCode;

@Component
@AllArgsConstructor
class ActivateUserCommandValidator implements IBaseValidator<ActivateUserCommand> {

    public static final String MESSAGE_KEY_ACTIVATION_TOKEN_NOT_FOUND = "activation.token.not.found";
    public static final String MESSAGE_KEY_ACTIVATION_TOKEN_EXPIRED = "activation.token.expired";

    private final ActivationTokenRepository tokenRepository;

    @Override
    public void validate(ActivateUserCommand command, ValidationErrorBuilder errorBuilder) {
        checkActivationTokenExists(command, errorBuilder);
        checkActivationTokenIsExpired(command, errorBuilder);
    }

    /**
     * Check if given token is expired
     */
    private void checkActivationTokenIsExpired(ActivateUserCommand command, ValidationErrorBuilder errorBuilder) {
        boolean tokenIsExpired = tokenRepository.checkActivationTokenIsExpired(command.token());
        if (tokenIsExpired) {
            errorBuilder.addError(
                    ActivateUserCommand.TOKEN,
                    MESSAGE_KEY_ACTIVATION_TOKEN_EXPIRED,
                    ErrorCode.ACTIVATION_TOKEN_EXPIRED
            );
        }
    }

    /**
     * Check if given token exists
     */
    private void checkActivationTokenExists(ActivateUserCommand command, ValidationErrorBuilder errorBuilder) {
        UserId userId = tokenRepository.getUserByActivationToken(command.token());
        if (userId == null) {
            errorBuilder.addError(
                    ActivateUserCommand.TOKEN,
                    MESSAGE_KEY_ACTIVATION_TOKEN_NOT_FOUND,
                    ErrorCode.OBJECT_NOT_EXISTS
            );
            errorBuilder.flush();
        }
    }
}
