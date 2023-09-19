package pl.edziennik.application.command.schoolclass.delete;

import jakarta.validation.constraints.NotNull;
import pl.edziennik.application.common.dispatcher.Command;
import pl.edziennik.application.common.dispatcher.Handler;
import pl.edziennik.common.valueobject.id.SchoolClassId;

/**
 * A command used for deleting existing school class
 */
@Handler(handler = DeleteSchoolClassCommandHandler.class, validator = DeleteSchoolClassCommandValidator.class)
public record DeleteSchoolClassCommand(
        @NotNull(message = "${field.empty}") SchoolClassId schoolClassId
) implements Command {

    public static final String SCHOOL_CLASS_ID = "schoolClassId";

}
