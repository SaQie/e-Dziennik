package pl.edziennik.eDziennik.infrastructure;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.domain.user.repository.UserRepository;
import pl.edziennik.eDziennik.infrastructure.spring.base.IBaseValidator;
import pl.edziennik.eDziennik.infrastructure.spring.dispatcher.ValidationErrorBuilder;
import pl.edziennik.eDziennik.infrastructure.validator.errorcode.ErrorCode;

@Service
@AllArgsConstructor
public class UpdateUserCommandValidator implements IBaseValidator<UpdateUserCommand> {

    private final UserRepository userRepository;

    @Override
    public void validate(UpdateUserCommand command, ValidationErrorBuilder validationErrorBuilder) {

        validationErrorBuilder.addError("DupaField", "Nie wiem", ErrorCode.OBJECT_NOT_EXISTS);
        validationErrorBuilder.addError("DupaField", "Nie wiem", ErrorCode.OBJECT_NOT_EXISTS);
        validationErrorBuilder.addError("DupaField", "Nie wiem", ErrorCode.OBJECT_NOT_EXISTS);


    }
}
