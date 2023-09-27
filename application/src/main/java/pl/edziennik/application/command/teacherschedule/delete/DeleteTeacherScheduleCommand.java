package pl.edziennik.application.command.teacherschedule.delete;

import com.fasterxml.jackson.annotation.JsonIgnore;
import pl.edziennik.application.common.dispatcher.Command;
import pl.edziennik.application.common.dispatcher.Handler;
import pl.edziennik.common.valueobject.id.TeacherScheduleId;

/**
 * A command used for deleting teacher schedules
 */
@Handler(handler = DeleteTeacherScheduleCommandHandler.class, validator = DeleteTeacherScheduleCommandValidator.class)
public record DeleteTeacherScheduleCommand(
        @JsonIgnore TeacherScheduleId teacherScheduleId
) implements Command {

    public static final String TEACHER_SCHEDULE_ID = "teacherScheduleId";

}
