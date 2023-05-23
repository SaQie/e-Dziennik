package pl.edziennik.application.command.user.activate;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.application.common.dispatcher.command.ICommandHandler;
import pl.edziennik.common.valueobject.id.UserId;
import pl.edziennik.domain.user.User;
import pl.edziennik.infrastructure.repository.ActivationTokenRepository;
import pl.edziennik.infrastructure.repository.user.UserQueryRepository;

@Component
@AllArgsConstructor
class ActivateUserCommandHandler implements ICommandHandler<ActivateUserCommand, OperationResult> {

    private final UserQueryRepository userQueryRepository;
    private final ActivationTokenRepository activationTokenRepository;

    @Override
    @Transactional
    public OperationResult handle(ActivateUserCommand command) {
        UserId userId = activationTokenRepository.getUserByActivationToken(command.token());
        User user = userQueryRepository.getUserByUserId(userId);

        user.activate();
        activationTokenRepository.deleteActivationToken(userId);

        return OperationResult.success();
    }
}
