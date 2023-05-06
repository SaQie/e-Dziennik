package pl.edziennik.eDziennik.infrastructure;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.domain.user.repository.UserRepository;
import pl.edziennik.eDziennik.infrastructure.spring.base.IBaseValidator;
import pl.edziennik.eDziennik.infrastructure.spring.dispatcher.ValidationResultBuilder;
import pl.edziennik.eDziennik.infrastructure.validator.errorcode.ErrorCode;

@Service
@AllArgsConstructor
public class UpdateUserCommandValidator implements IBaseValidator<UpdateUserCommand> {

    private final UserRepository userRepository;

    @Override
    public void validate(UpdateUserCommand command, ValidationResultBuilder validationResultBuilder) {

        validationResultBuilder.addError("DupaField", "Nie wiem", ErrorCode.OBJECT_NOT_EXISTS);
        validationResultBuilder.addError("DupaField", "Nie wiem", ErrorCode.OBJECT_NOT_EXISTS);
        validationResultBuilder.addError("DupaField", "Nie wiem", ErrorCode.OBJECT_NOT_EXISTS);


    }
}
