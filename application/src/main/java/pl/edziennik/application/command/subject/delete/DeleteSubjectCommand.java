package pl.edziennik.application.command.subject.delete;

import jakarta.validation.constraints.NotNull;
import pl.edziennik.application.common.dispatcher.Command;
import pl.edziennik.application.common.dispatcher.Handler;
import pl.edziennik.common.valueobject.id.SubjectId;

/**
 * A command used for deleting existing subject
 */
@Handler(handler = DeleteSubjectCommandHandler.class, validator = DeleteSubjectCommandValidator.class)
public record DeleteSubjectCommand(
        @NotNull(message = "${field.empty}") SubjectId subjectId
) implements Command {

    public static final String SUBJECT_ID = "subjectId";

}
