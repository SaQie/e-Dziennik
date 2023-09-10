package pl.edziennik.application.command.parent.create;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.ValidationErrorBuilder;
import pl.edziennik.application.common.dispatcher.Validator;
import pl.edziennik.infrastructure.repository.student.StudentCommandRepository;
import pl.edziennik.infrastructure.repository.user.UserCommandRepository;
import pl.edziennik.infrastructure.validator.errorcode.ErrorCode;

@Component
@AllArgsConstructor
class CreateParentCommandValidator implements Validator<CreateParentCommand> {

    public static final String MESSAGE_KEY_STUDENT_ALREADY_HAS_PARENT = "student.already.has.parent";
    public static final String MESSAGE_KEY_USER_ALREADY_EXISTS_BY_EMAIL = "user.already.exists.by.email";
    public static final String MESSAGE_KEY_USER_ALREADY_EXISTS_BY_USERNAME = "user.already.exists";
    public static final String MESSAGE_KEY_ACCOUNT_INACTIVE = "account.inactive";

    private final StudentCommandRepository studentCommandRepository;
    private final UserCommandRepository userCommandRepository;

    @Override
    public void validate(CreateParentCommand command, ValidationErrorBuilder errorBuilder) {
        if (command.studentId() != null) {
            checkStudentExists(command, errorBuilder);
            checkStudentAccountIsActive(command, errorBuilder);

            checkStudentHasAlreadyAssignedParent(command, errorBuilder);
        }

        checkUserByEmailAlreadyExists(command, errorBuilder);
        checkUserByUsernameAlreadyExists(command, errorBuilder);
    }

    /**
     * Check user with given username already exists
     */
    private void checkUserByUsernameAlreadyExists(CreateParentCommand command, ValidationErrorBuilder errorBuilder) {
        if (userCommandRepository.existsByUsername(command.username())) {
            errorBuilder.addError(
                    CreateParentCommand.USERNAME,
                    MESSAGE_KEY_USER_ALREADY_EXISTS_BY_USERNAME,
                    ErrorCode.OBJECT_ALREADY_EXISTS,
                    command.username());
        }
    }

    /**
     * Check user with given email already exists
     */
    private void checkUserByEmailAlreadyExists(CreateParentCommand command, ValidationErrorBuilder errorBuilder) {
        if (userCommandRepository.existsByEmail(command.email())) {
            errorBuilder.addError(
                    CreateParentCommand.EMAIL,
                    MESSAGE_KEY_USER_ALREADY_EXISTS_BY_EMAIL,
                    ErrorCode.OBJECT_ALREADY_EXISTS,
                    command.email());
        }
    }

    /**
     * Check student account is active
     */
    private void checkStudentAccountIsActive(CreateParentCommand command, ValidationErrorBuilder errorBuilder) {
        if (studentCommandRepository.isStudentAccountNotActive(command.studentId())) {
            errorBuilder.addError(
                    CreateParentCommand.STUDENT_ID,
                    MESSAGE_KEY_ACCOUNT_INACTIVE,
                    ErrorCode.ACCOUNT_INACTIVE
            );
        }
    }

    /**
     * Check student has already assigned parent
     */
    private void checkStudentHasAlreadyAssignedParent(CreateParentCommand command, ValidationErrorBuilder errorBuilder) {
        if (studentCommandRepository.hasAlreadyAssignedParent(command.studentId())) {
            errorBuilder.addError(
                    CreateParentCommand.STUDENT_ID,
                    MESSAGE_KEY_STUDENT_ALREADY_HAS_PARENT,
                    ErrorCode.STUDENT_HAS_PARENT
            );
        }
    }

    /**
     * Check student exists
     */
    private void checkStudentExists(CreateParentCommand command, ValidationErrorBuilder errorBuilder) {
        studentCommandRepository.findById(command.studentId())
                .orElseGet(() -> {
                    errorBuilder.addNotFoundError(CreateParentCommand.STUDENT_ID);
                    return null;
                });

        errorBuilder.flush();
    }
}
