package pl.edziennik.application.command.user.activate;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.application.common.dispatcher.ICommandHandler;
import pl.edziennik.common.valueobject.id.UserId;
import pl.edziennik.domain.user.User;
import pl.edziennik.infrastructure.repository.token.ActivationTokenRepository;
import pl.edziennik.infrastructure.repository.user.UserCommandRepository;

@Component
@AllArgsConstructor
class ActivateUserCommandHandler implements ICommandHandler<ActivateUserCommand, OperationResult> {

    private final UserCommandRepository userCommandRepository;
    private final ActivationTokenRepository activationTokenRepository;

    @Override
    @Transactional
    public OperationResult handle(ActivateUserCommand command) {
        UserId userId = activationTokenRepository.getUserByActivationToken(command.token());
        User user = userCommandRepository.getUserByUserId(userId);

        user.activate();
        activationTokenRepository.deleteActivationToken(userId);

        return OperationResult.success();
    }
}
