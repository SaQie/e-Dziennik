package pl.edziennik.application.command.subjectmanagment.assigntostudent;

import jakarta.validation.constraints.NotNull;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.application.common.dispatcher.base.HandledBy;
import pl.edziennik.application.common.dispatcher.base.ValidatedBy;
import pl.edziennik.application.common.dispatcher.command.ICommand;
import pl.edziennik.common.valueobject.id.StudentId;
import pl.edziennik.common.valueobject.id.SubjectId;

@HandledBy(handler = AssignSubjectToStudentCommandHandler.class)
@ValidatedBy(validator = AssignSubjectToStudentCommandValidator.class)
public record AssignSubjectToStudentCommand(

        @NotNull(message = "{student.empty}") StudentId studentId,
        @NotNull(message = "{subject.empty}") SubjectId subjectId

) implements ICommand<OperationResult> {

    public static final String STUDENT_ID = "studentId";
    public static final String SUBJECT_ID = "subjectId";

}
