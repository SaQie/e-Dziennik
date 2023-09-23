package pl.edziennik.application.command.classroomschedule.create;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import pl.edziennik.application.common.dispatcher.Command;
import pl.edziennik.application.common.dispatcher.Handler;
import pl.edziennik.common.valueobject.id.ClassRoomId;
import pl.edziennik.common.valueobject.id.ClassRoomScheduleId;
import pl.edziennik.common.valueobject.vo.Description;

import java.time.LocalDateTime;

/**
 * A command used for add something on class-room schedule
 */
@Handler(handler = CreateClassRoomScheduleCommandHandler.class, validator = CreateClassRoomScheduleCommandValidator.class)
public record CreateClassRoomScheduleCommand(

        @JsonIgnore ClassRoomScheduleId classRoomScheduleId,
        @JsonIgnore ClassRoomId classRoomId,
        @Schema(example = "Teacher Kamil Nowak class-room")
        @Valid @NotNull(message = "{field.empty}") Description description,

        @Schema(example = "2023-01-01 9:00:00")
        @Valid @NotNull(message = "{field.empty}") @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startDate,

        @Schema(example = "2023-01-01 9:45:00")
        @Valid @NotNull(message = "{field.empty}") @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endDate

) implements Command {

    public static final String CLASS_ROOM_ID = "classRoomId";
    public static final String DESCRIPTION = "description";
    public static final String START_DATE = "startDate";
    public static final String END_DATE = "endDate";
    public static final String CLASS_ROOM_SCHEDULE_ID = "classRoomScheduleId";

    @JsonCreator
    public CreateClassRoomScheduleCommand(ClassRoomId classRoomId, Description description,
                                          LocalDateTime startDate, LocalDateTime endDate) {
        this(ClassRoomScheduleId.create(), classRoomId, description, startDate, endDate);
    }

    public CreateClassRoomScheduleCommand(ClassRoomId classRoomId, CreateClassRoomScheduleCommand command) {
        this(ClassRoomScheduleId.create(), classRoomId, command.description, command.startDate, command.endDate);
    }
}
