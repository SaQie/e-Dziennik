package pl.edziennik.application.command.subjectmanagment.unassign;

import io.swagger.v3.oas.annotations.media.Schema;
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

        @Schema(example = "395a1ca8-59e0-11ee-8c99-0242ac120002")
        @NotNull(message = "{student.empty}") StudentId studentId,

        @Schema(example = "395a1ca8-59e0-11ee-8c99-0242ac120002")
        @NotNull(message = "{subject.empty}") SubjectId subjectId

) implements Command {

    public static final String STUDENT_ID = "studentId";
    public static final String SUBJECT_ID = "subjectId";

}
