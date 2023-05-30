package pl.edziennik.application.command.student.assignparent;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.ValidationErrorBuilder;
import pl.edziennik.application.common.dispatcher.base.IBaseValidator;
import pl.edziennik.infrastructure.repository.parent.ParentCommandRepository;
import pl.edziennik.infrastructure.repository.student.StudentCommandRepository;
import pl.edziennik.infrastructure.validator.errorcode.ErrorCode;

@Component
@AllArgsConstructor
class AssignParentCommandValidator implements IBaseValidator<AssignParentCommand> {

    public static final String MESSAGE_KEY_PARENT_ALREADY_HAS_STUDENT = "parent.already.has.student";
    public static final String MESSAGE_KEY_STUDENT_ALREADY_HAS_PARENT = "student.already.has.parent";
    public static final String MESSAGE_KEY_ACCOUNT_INACTIVE = "account.inactive";


    private final ParentCommandRepository parentCommandRepository;
    private final StudentCommandRepository studentCommandRepository;

    @Override
    public void validate(AssignParentCommand command, ValidationErrorBuilder errorBuilder) {
        checkParentExists(command, errorBuilder);
        checkStudentExists(command, errorBuilder);

        errorBuilder.flush();

        checkParentAccountIsActive(command, errorBuilder);
        checkStudentAccountIsActive(command, errorBuilder);
        checkParentHasAlreadyAssignedStudent(command, errorBuilder);
        checkStudentHasAlreadyAssignedParent(command, errorBuilder);

    }

    /**
     * Check if student has already assigned parent
     */
    private void checkStudentHasAlreadyAssignedParent(AssignParentCommand command, ValidationErrorBuilder errorBuilder) {
        if (studentCommandRepository.hasAlreadyAssignedParent(command.studentId())) {
            errorBuilder.addError(
                    AssignParentCommand.STUDENT_ID,
                    MESSAGE_KEY_STUDENT_ALREADY_HAS_PARENT,
                    ErrorCode.STUDENT_HAS_PARENT
            );
        }
    }

    /**
     * Check if parent has already assigned student
     */
    private void checkParentHasAlreadyAssignedStudent(AssignParentCommand command, ValidationErrorBuilder errorBuilder) {
        if (parentCommandRepository.hasAlreadyAssignedStudent(command.parentId())) {
            errorBuilder.addError(
                    AssignParentCommand.PARENT_ID,
                    MESSAGE_KEY_PARENT_ALREADY_HAS_STUDENT,
                    ErrorCode.PARENT_HAS_STUDENT
            );
        }
    }

    /**
     * Check if student account is active
     */
    private void checkStudentAccountIsActive(AssignParentCommand command, ValidationErrorBuilder errorBuilder) {
        if (studentCommandRepository.isStudentAccountNotActive(command.studentId())) {
            errorBuilder.addError(
                    AssignParentCommand.STUDENT_ID,
                    MESSAGE_KEY_ACCOUNT_INACTIVE,
                    ErrorCode.ACCOUNT_INACTIVE
            );
        }
    }

    /**
     * Check if parent account is active
     */
    private void checkParentAccountIsActive(AssignParentCommand command, ValidationErrorBuilder errorBuilder) {
        if (parentCommandRepository.isParentAccountNotActive(command.parentId())) {
            errorBuilder.addError(
                    AssignParentCommand.PARENT_ID,
                    MESSAGE_KEY_ACCOUNT_INACTIVE,
                    ErrorCode.ACCOUNT_INACTIVE
            );
        }
    }

    /**
     * Check if student exists
     */
    private void checkStudentExists(AssignParentCommand command, ValidationErrorBuilder errorBuilder) {
        studentCommandRepository.findById(command.studentId())
                .orElseGet(() -> {
                    errorBuilder.addNotFoundError(AssignParentCommand.STUDENT_ID);
                    return null;
                });
    }

    /**
     * Check if parent exists
     */
    private void checkParentExists(AssignParentCommand command, ValidationErrorBuilder errorBuilder) {
        parentCommandRepository.findById(command.parentId())
                .orElseGet(() -> {
                    errorBuilder.addNotFoundError(AssignParentCommand.PARENT_ID);
                    return null;
                });
    }
}
