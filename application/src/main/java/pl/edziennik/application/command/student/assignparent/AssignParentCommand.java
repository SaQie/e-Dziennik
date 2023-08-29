package pl.edziennik.application.command.student.assignparent;

import jakarta.validation.constraints.NotNull;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.application.common.dispatcher.HandledBy;
import pl.edziennik.application.common.dispatcher.ValidatedBy;
import pl.edziennik.application.common.dispatcher.ICommand;
import pl.edziennik.common.valueobject.id.ParentId;
import pl.edziennik.common.valueobject.id.StudentId;

/**
 * A command used for assigning the parent account with the student account
 */
@HandledBy(handler = AssignParentCommandHandler.class)
@ValidatedBy(validator = AssignParentCommandValidator.class)
public record AssignParentCommand(

        @NotNull(message = "{student.empty}") StudentId studentId,
        @NotNull(message = "{parent.empty}") ParentId parentId

) implements ICommand<OperationResult> {

    public static final String STUDENT_ID = "studentId";
    public static final String PARENT_ID = "parentId";

}
