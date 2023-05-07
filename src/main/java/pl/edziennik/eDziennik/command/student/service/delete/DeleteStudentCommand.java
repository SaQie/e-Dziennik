package pl.edziennik.eDziennik.command.student.service.delete;

import pl.edziennik.eDziennik.domain.student.domain.wrapper.StudentId;
import pl.edziennik.eDziennik.infrastructure.OperationResult;
import pl.edziennik.eDziennik.infrastructure.spring.base.HandledBy;
import pl.edziennik.eDziennik.infrastructure.spring.command.ICommand;

@HandledBy(handler = DeleteStudentCommandHandler.class)
public record DeleteStudentCommand(
        StudentId studentId
) implements ICommand<OperationResult> {

    public static final String STUDENT_ID = "studentId";

}
