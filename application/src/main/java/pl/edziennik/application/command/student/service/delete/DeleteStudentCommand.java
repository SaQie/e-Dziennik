package pl.edziennik.application.command.student.service.delete;


import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.application.common.dispatcher.base.HandledBy;
import pl.edziennik.application.common.dispatcher.command.ICommand;
import pl.edziennik.domain.student.StudentId;

@HandledBy(handler = DeleteStudentCommandHandler.class)
public record DeleteStudentCommand(
        StudentId studentId
) implements ICommand<OperationResult> {

    public static final String STUDENT_ID = "studentId";

}
