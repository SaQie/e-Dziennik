package pl.edziennik.application.command.user.createuser;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edziennik.application.common.dispatcher.ValidationErrorBuilder;
import pl.edziennik.application.common.dispatcher.base.IBaseValidator;
import pl.edziennik.infrastructure.spring.ResourceCreator;

@Service
@AllArgsConstructor
public class CreateUserCommandValidator implements IBaseValidator<CreateUserCommand> {


    private final ResourceCreator res;

    @Override
    public void validate(CreateUserCommand command, ValidationErrorBuilder errorBuilder) {

    }
}
