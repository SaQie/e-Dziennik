package pl.edziennik.application.command.school.delete;

import jakarta.validation.constraints.NotNull;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.application.common.dispatcher.base.HandledBy;
import pl.edziennik.application.common.dispatcher.base.ValidatedBy;
import pl.edziennik.application.common.dispatcher.command.ICommand;
import pl.edziennik.common.valueobject.id.SchoolId;

/**
 * A command used for deleting the existing school
 */
@HandledBy(handler = DeleteSchoolCommandHandler.class)
@ValidatedBy(validator = DeleteSchoolCommandValidator.class)
public record DeleteSchoolCommand(

        @NotNull(message = "{school.empty}") SchoolId schoolId

) implements ICommand<OperationResult> {

    public static final String SCHOOL_ID = "schoolId";

}
