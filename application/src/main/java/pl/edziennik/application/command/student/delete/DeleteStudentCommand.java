package pl.edziennik.application.command.student.delete;


import jakarta.validation.constraints.NotNull;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.application.common.dispatcher.base.HandledBy;
import pl.edziennik.application.common.dispatcher.command.ICommand;
import pl.edziennik.common.valueobject.id.StudentId;

/**
 * A command used for deleting the existing user account
 */
@HandledBy(handler = DeleteStudentCommandHandler.class)
public record DeleteStudentCommand(

        @NotNull(message = "{student.empty}") StudentId studentId

) implements ICommand<OperationResult> {

    public static final String STUDENT_ID = "studentId";

}
