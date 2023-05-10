package pl.edziennik.application.command.student.delete;


import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.application.common.dispatcher.base.HandledBy;
import pl.edziennik.application.common.dispatcher.command.ICommand;
import pl.edziennik.common.valueobject.id.StudentId;

@HandledBy(handler = DeleteStudentCommandHandler.class)
public record DeleteStudentCommand(
        StudentId studentId
) implements ICommand<OperationResult> {

    public static final String STUDENT_ID = "studentId";

}
