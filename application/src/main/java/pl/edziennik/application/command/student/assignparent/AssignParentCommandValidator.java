package pl.edziennik.application.command.student.assignparent;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.ValidationErrorBuilder;
import pl.edziennik.application.common.dispatcher.base.IBaseValidator;
import pl.edziennik.infrastructure.repositories.parent.ParentCommandRepository;
import pl.edziennik.infrastructure.repositories.student.StudentCommandRepository;
import pl.edziennik.infrastructure.spring.ResourceCreator;
import pl.edziennik.infrastructure.validator.errorcode.ErrorCode;

@Component
@AllArgsConstructor
class AssignParentCommandValidator implements IBaseValidator<AssignParentCommand> {

    private final ParentCommandRepository parentCommandRepository;
    private final StudentCommandRepository studentCommandRepository;
    private final ResourceCreator res;

    @Override
    public void validate(AssignParentCommand command, ValidationErrorBuilder errorBuilder) {
        parentCommandRepository.findById(command.parentId())
                .orElseGet(() -> {
                    errorBuilder.addNotFoundError(AssignParentCommand.STUDENT_ID);
                    return null;
                });

        studentCommandRepository.findById(command.studentId())
                .orElseGet(() -> {
                    errorBuilder.addNotFoundError(AssignParentCommand.PARENT_ID);
                    return null;
                });

        errorBuilder.flushErrors();

        if (parentCommandRepository.hasAlreadyAssignedStudent(command.parentId())) {
            errorBuilder.addError(
                    AssignParentCommand.PARENT_ID,
                    "parent.already.has.student",
                    ErrorCode.PARENT_HAS_STUDENT
            );
        }

        if (studentCommandRepository.hasAlreadyAssignedParent(command.studentId())) {
            errorBuilder.addError(
                    AssignParentCommand.STUDENT_ID,
                    "student.already.has.parent",
                    ErrorCode.STUDENT_HAS_PARENT
            );
        }

    }
}
