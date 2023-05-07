package pl.edziennik.eDziennik.command.user.createuser;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.infrastructure.spring.base.IBaseValidator;
import pl.edziennik.eDziennik.infrastructure.spring.dispatcher.ValidationErrorBuilder;
import pl.edziennik.eDziennik.server.utils.ResourceCreator;

@Service
@AllArgsConstructor
public class CreateUserCommandValidator implements IBaseValidator<CreateUserCommand> {


    private final ResourceCreator res;

    @Override
    public void validate(CreateUserCommand command, ValidationErrorBuilder errorBuilder) {

    }
}
