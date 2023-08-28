package pl.edziennik.application.command.school.changeconfig;

import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.application.common.dispatcher.base.HandledBy;
import pl.edziennik.application.common.dispatcher.command.ICommand;
import pl.edziennik.common.enums.AverageType;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.valueobject.vo.TimeFrameDuration;

/**
 * A command used for changing the school configuration values
 */
@HandledBy(handler = ChangeSchoolConfigurationValuesCommandHandler.class)
public record ChangeSchoolConfigurationValuesCommand(

        SchoolId schoolId,
        AverageType averageType,
        TimeFrameDuration maxLessonTime,
        TimeFrameDuration minScheduleTime


) implements ICommand<OperationResult> {

    public static final String SCHOOL_ID = "schoolId";
    public static final String AVERAGE_TYPE = "averageType";
    public static final String MAX_LESSON_TIME = "maxLessonTime";
    public static final String MIN_SCHEDULE_TIME = "minScheduleTime";

}
