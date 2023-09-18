package pl.edziennik.application.command.subjectmanagment.unassign;

import jakarta.validation.constraints.NotNull;
import pl.edziennik.application.common.dispatcher.Command;
import pl.edziennik.application.common.dispatcher.Handler;
import pl.edziennik.common.valueobject.id.StudentId;
import pl.edziennik.common.valueobject.id.SubjectId;

/**
 * Command used for unassign student from subject
 */
@Handler(handler = UnassignStudentFromSubjectCommandHandler.class, validator = UnassignStudentFromSubjectCommandValidator.class)
public record UnassignStudentFromSubjectCommand(

        @NotNull(message = "{student.empty}") StudentId studentId,
        @NotNull(message = "{subject.empty}") SubjectId subjectId

) implements Command {

    public static final String STUDENT_ID = "studentId";
    public static final String SUBJECT_ID = "subjectId";

}
