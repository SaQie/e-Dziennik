package pl.edziennik.application.command.grademanagment.unassign;

import jakarta.validation.constraints.NotNull;
import pl.edziennik.application.common.dispatcher.Command;
import pl.edziennik.application.common.dispatcher.Handler;
import pl.edziennik.common.valueobject.id.GradeId;

/**
 * A command used for unassign grade from student subject
 */
@Handler(handler = UnassignGradeFromStudentSubjectCommandHandler.class)
public record UnassignGradeFromStudentSubjectCommand(
        @NotNull(message = "${field.empty}") GradeId gradeId
) implements Command {
    public static final String GRADE_ID = "gradeId";
}
