package pl.edziennik.application.command.classroomschedule.delete;

import com.fasterxml.jackson.annotation.JsonIgnore;
import pl.edziennik.application.common.dispatcher.Command;
import pl.edziennik.application.common.dispatcher.Handler;
import pl.edziennik.common.valueobject.id.ClassRoomScheduleId;

/**
 * A command used for delete class room schedule
 */
@Handler(handler = DeleteClassRoomScheduleCommandHandler.class, validator = DeleteClassRoomScheduleCommandValidator.class)
public record DeleteClassRoomScheduleCommand(
        @JsonIgnore ClassRoomScheduleId classRoomScheduleId
) implements Command {

    public static final String CLASS_ROOM_SCHEDULE_ID = "classRoomScheduleId";

}
