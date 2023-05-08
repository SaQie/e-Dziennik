package pl.edziennik.application.command.student.assignparent;

import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.application.common.dispatcher.base.HandledBy;
import pl.edziennik.application.common.dispatcher.base.ValidatedBy;
import pl.edziennik.application.common.dispatcher.command.ICommand;
import pl.edziennik.domain.parent.ParentId;
import pl.edziennik.domain.student.StudentId;

@HandledBy(handler = AssignParentCommandHandler.class)
@ValidatedBy(validator = AssignParentCommandValidator.class)
public record AssignParentCommand(
        StudentId studentId,
        ParentId parentId
) implements ICommand<OperationResult> {

    public static final String STUDENT_ID = "studentId";
    public static final String PARENT_ID = "parentId";

}
