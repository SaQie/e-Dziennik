package pl.edziennik.application.command.admin;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.ValidationErrorBuilder;
import pl.edziennik.application.common.dispatcher.base.IBaseValidator;
import pl.edziennik.infrastructure.repositories.admin.AdminCommandRepository;
import pl.edziennik.infrastructure.validator.errorcode.ErrorCode;

@Component
@AllArgsConstructor
class CreateAdminCommandValidator implements IBaseValidator<CreateAdminCommand> {

    private final AdminCommandRepository adminCommandRepository;

    @Override
    public void validate(CreateAdminCommand command, ValidationErrorBuilder errorBuilder) {
        if (adminCommandRepository.isAdminAccountAlreadyExists()) {
            errorBuilder.addError(CreateAdminCommand.USERNAME,
                    "admin.already.exist",
                    ErrorCode.OBJECT_ALREADY_EXISTS);
        }
    }
}
