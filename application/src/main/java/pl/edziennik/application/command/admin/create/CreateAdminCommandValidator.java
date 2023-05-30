package pl.edziennik.application.command.admin.create;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.ValidationErrorBuilder;
import pl.edziennik.application.common.dispatcher.base.IBaseValidator;
import pl.edziennik.infrastructure.repository.admin.AdminCommandRepository;
import pl.edziennik.infrastructure.validator.errorcode.ErrorCode;

@Component
@AllArgsConstructor
class CreateAdminCommandValidator implements IBaseValidator<CreateAdminCommand> {

    public static final String MESSAGE_KEY_ADMIN_ALREADY_EXISTS = "admin.already.exists";

    private final AdminCommandRepository adminCommandRepository;

    @Override
    public void validate(CreateAdminCommand command, ValidationErrorBuilder errorBuilder) {
        checkAdminAccountAlreadyExists(errorBuilder);
    }

    /**
     * Check admin account already exists in system (only one can exists)
     */
    private void checkAdminAccountAlreadyExists(ValidationErrorBuilder errorBuilder) {
        if (adminCommandRepository.isAdminAccountAlreadyExists()) {
            errorBuilder.addError(CreateAdminCommand.USERNAME,
                    MESSAGE_KEY_ADMIN_ALREADY_EXISTS,
                    ErrorCode.OBJECT_ALREADY_EXISTS);
        }
    }
}
