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


    private final ParentCommandRepository parentCommandRepository;
    private final StudentCommandRepository studentCommandRepository;

    @Override
    public void validate(AssignParentCommand command, ValidationErrorBuilder errorBuilder) {
        parentCommandRepository.findById(command.parentId())
                .orElseGet(() -> {
                    errorBuilder.addNotFoundError(AssignParentCommand.PARENT_ID);
                    return null;
                });

        studentCommandRepository.findById(command.studentId())
                .orElseGet(() -> {
                    errorBuilder.addNotFoundError(AssignParentCommand.STUDENT_ID);
                    return null;
                });

        errorBuilder.flush();

        if (parentCommandRepository.hasAlreadyAssignedStudent(command.parentId())) {
            errorBuilder.addError(
                    AssignParentCommand.PARENT_ID,
                    MESSAGE_KEY_PARENT_ALREADY_HAS_STUDENT,
                    ErrorCode.PARENT_HAS_STUDENT
            );
        }

        if (studentCommandRepository.hasAlreadyAssignedParent(command.studentId())) {
            errorBuilder.addError(
                    AssignParentCommand.STUDENT_ID,
                    MESSAGE_KEY_STUDENT_ALREADY_HAS_PARENT,
                    ErrorCode.STUDENT_HAS_PARENT
            );
        }

    }
}
