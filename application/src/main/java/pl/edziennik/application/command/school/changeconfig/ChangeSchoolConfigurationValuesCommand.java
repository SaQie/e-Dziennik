package pl.edziennik.application.command.school.changeconfig;

import jakarta.validation.constraints.NotNull;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.application.common.dispatcher.base.HandledBy;
import pl.edziennik.application.common.dispatcher.command.ICommand;
import pl.edziennik.common.enums.AverageType;
import pl.edziennik.common.valueobject.id.SchoolId;

/**
 * A command used for changing the school configuration values
 */
@HandledBy(handler = ChangeSchoolConfigurationValuesCommandHandler.class)
public record ChangeSchoolConfigurationValuesCommand(

        SchoolId schoolId,
        @NotNull(message = "{field.empty}") AverageType averageType

) implements ICommand<OperationResult> {

    public static final String SCHOOL_ID = "schoolId";
    public static final String AVERAGE_TYPE = "averageType";

}
