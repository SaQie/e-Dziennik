package pl.edziennik.application.command.student.assignparent;

import io.swagger.v3.oas.annotations.media.Schema;
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

        @Schema(example = "395a1ca8-59e0-11ee-8c99-0242ac120002")
        @NotNull(message = "{student.empty}") StudentId studentId,

        @Schema(example = "395a1ca8-59e0-11ee-8c99-0242ac120002")
        @NotNull(message = "{parent.empty}") ParentId parentId

) implements Command {

    public static final String STUDENT_ID = "studentId";
    public static final String PARENT_ID = "parentId";

}
