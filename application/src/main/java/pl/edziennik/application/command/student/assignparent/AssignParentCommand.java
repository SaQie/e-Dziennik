package pl.edziennik.application.command.student.assignparent;

import jakarta.validation.constraints.NotNull;
import pl.edziennik.application.common.dispatcher.Command;
import pl.edziennik.application.common.dispatcher.Handler;
import pl.edziennik.common.valueobject.id.ParentId;
import pl.edziennik.common.valueobject.id.StudentId;

/**
 * A command used for assigning the parent account with the student account
 */
@Handler(handler = AssignParentCommandHandler.class, validator = AssignParentCommandValidator.class)
public record AssignParentCommand(

        @NotNull(message = "{student.empty}") StudentId studentId,
        @NotNull(message = "{parent.empty}") ParentId parentId

) implements Command {

    public static final String STUDENT_ID = "studentId";
    public static final String PARENT_ID = "parentId";

}
