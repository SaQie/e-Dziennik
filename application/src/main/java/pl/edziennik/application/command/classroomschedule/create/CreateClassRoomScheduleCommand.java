package pl.edziennik.application.command.classroomschedule.create;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.application.common.dispatcher.base.HandledBy;
import pl.edziennik.application.common.dispatcher.base.ValidatedBy;
import pl.edziennik.application.common.dispatcher.command.ICommand;
import pl.edziennik.common.valueobject.id.ClassRoomId;
import pl.edziennik.common.valueobject.vo.Description;

import java.time.LocalDateTime;

/**
 * A command used for add something on class-room schedule
 */
@HandledBy(handler = CreateClassRoomScheduleCommandHandler.class)
@ValidatedBy(validator = CreateClassRoomScheduleCommandValidator.class)
public record CreateClassRoomScheduleCommand(

        @JsonIgnore ClassRoomId classRoomId,
        @Valid @NotNull(message = "{field.empty}") Description description,
        @Valid @NotNull(message = "{field.empty}") @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startDate,
        @Valid @NotNull(message = "{field.empty}") @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endDate

) implements ICommand<OperationResult> {

    public static final String CLASS_ROOM_ID = "classRoomId";
    public static final String DESCRIPTION = "description";
    public static final String START_DATE = "startDate";
    public static final String END_DATE = "endDate";

    public CreateClassRoomScheduleCommand(ClassRoomId classRoomId, CreateClassRoomScheduleCommand command) {
        this(classRoomId, command.description, command.startDate, command.endDate);
    }
}
