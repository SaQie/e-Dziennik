package pl.edziennik.application.command.user.changeuserdata;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.application.common.dispatcher.ICommandHandler;
import pl.edziennik.domain.user.User;
import pl.edziennik.infrastructure.repository.user.UserCommandRepository;

@Component
@AllArgsConstructor
class ChangeUserDataCommandHandler implements ICommandHandler<ChangeUserDataCommand, OperationResult> {

    private final UserCommandRepository userCommandRepository;


    @Override
    public OperationResult handle(ChangeUserDataCommand command) {
        User user = userCommandRepository.getUserByUserId(command.userId());

        user.changeUserData(command.username(), command.email());

        userCommandRepository.save(user);

        return OperationResult.success();
    }
}
