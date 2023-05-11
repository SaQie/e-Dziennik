package pl.edziennik.application.command.school.delete;

import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.application.common.dispatcher.base.HandledBy;
import pl.edziennik.application.common.dispatcher.base.ValidatedBy;
import pl.edziennik.application.common.dispatcher.command.ICommand;
import pl.edziennik.common.valueobject.id.SchoolId;

@HandledBy(handler = DeleteSchoolCommandHandler.class)
@ValidatedBy(validator = DeleteSchoolCommandValidator.class)
public record DeleteSchoolCommand(

        SchoolId schoolId

) implements ICommand<OperationResult> {

    public static final String SCHOOL_ID = "schoolId";

}
