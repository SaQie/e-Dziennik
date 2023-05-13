package pl.edziennik.application.command.parent.create;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.ValidationErrorBuilder;
import pl.edziennik.application.common.dispatcher.base.IBaseValidator;
import pl.edziennik.infrastructure.repositories.parent.ParentCommandRepository;
import pl.edziennik.infrastructure.repositories.student.StudentCommandRepository;
import pl.edziennik.infrastructure.validator.errorcode.ErrorCode;

@Component
@AllArgsConstructor
class CreateParentCommandValidator implements IBaseValidator<CreateParentCommand> {

    public static final String MESSAGE_KEY_STUDENT_ALREADY_HAS_PARENT = "student.already.has.parent";
    public static final String MESSAGE_KEY_USER_ALREADY_EXISTS_BY_EMAIL = "user.already.exists.by.email";
    public static final String MESSAGE_KEY_USER_ALREADY_EXISTS_BY_USERNAME = "user.already.exists";


    private final ParentCommandRepository parentCommandRepository;
    private final StudentCommandRepository studentCommandRepository;

    @Override
    public void validate(CreateParentCommand command, ValidationErrorBuilder errorBuilder) {
        if (command.studentId() != null) {
            studentCommandRepository.findById(command.studentId())
                    .orElseGet(() -> {
                        errorBuilder.addNotFoundError(CreateParentCommand.STUDENT_ID);
                        return null;
                    });

            errorBuilder.flush();

            if (studentCommandRepository.hasAlreadyAssignedParent(command.studentId())) {
                errorBuilder.addError(
                        CreateParentCommand.STUDENT_ID,
                        MESSAGE_KEY_STUDENT_ALREADY_HAS_PARENT,
                        ErrorCode.STUDENT_HAS_PARENT
                );
            }
        }

        if (parentCommandRepository.existsByEmail(command.email())) {
            errorBuilder.addError(
                    CreateParentCommand.EMAIL,
                    MESSAGE_KEY_USER_ALREADY_EXISTS_BY_EMAIL,
                    ErrorCode.OBJECT_ALREADY_EXISTS,
                    command.email());
        }

        if (parentCommandRepository.existsByUsername(command.username())) {
            errorBuilder.addError(
                    CreateParentCommand.USERNAME,
                    MESSAGE_KEY_USER_ALREADY_EXISTS_BY_USERNAME,
                    ErrorCode.OBJECT_ALREADY_EXISTS,
                    command.username());
        }
    }
}
