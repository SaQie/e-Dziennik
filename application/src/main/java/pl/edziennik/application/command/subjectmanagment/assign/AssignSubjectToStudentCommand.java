package pl.edziennik.application.command.subjectmanagment.assign;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import pl.edziennik.application.common.dispatcher.Command;
import pl.edziennik.application.common.dispatcher.Handler;
import pl.edziennik.common.valueobject.id.StudentId;
import pl.edziennik.common.valueobject.id.SubjectId;

/**
 * A command used for assigning the subject to the student
 */
@Handler(handler = AssignSubjectToStudentCommandHandler.class, validator = AssignSubjectToStudentCommandValidator.class)
public record AssignSubjectToStudentCommand(

        @Schema(example = "395a1ca8-59e0-11ee-8c99-0242ac120002")
        @NotNull(message = "{student.empty}") StudentId studentId,

        @Schema(example = "395a1ca8-59e0-11ee-8c99-0242ac120002")
        @NotNull(message = "{subject.empty}") SubjectId subjectId

) implements Command {

    public static final String STUDENT_ID = "studentId";
    public static final String SUBJECT_ID = "subjectId";

}
