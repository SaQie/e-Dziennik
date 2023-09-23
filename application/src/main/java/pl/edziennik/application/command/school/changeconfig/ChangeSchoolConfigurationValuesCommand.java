package pl.edziennik.application.command.school.changeconfig;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import pl.edziennik.application.common.dispatcher.Command;
import pl.edziennik.application.common.dispatcher.Handler;
import pl.edziennik.common.enums.AverageType;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.valueobject.vo.TimeFrameDuration;

/**
 * A command used for changing the school configuration values
 */
@Handler(handler = ChangeSchoolConfigurationValuesCommandHandler.class)
public record ChangeSchoolConfigurationValuesCommand(

        @JsonIgnore SchoolId schoolId,

        @Schema(example = "WEIGHTED")
        AverageType averageType,

        @Schema(example = "45")
        TimeFrameDuration maxLessonTime,

        @Schema(example = "30")
        TimeFrameDuration minScheduleTime


) implements Command {

    public static final String SCHOOL_ID = "schoolId";
    public static final String AVERAGE_TYPE = "averageType";
    public static final String MAX_LESSON_TIME = "maxLessonTime";
    public static final String MIN_SCHEDULE_TIME = "minScheduleTime";

}
