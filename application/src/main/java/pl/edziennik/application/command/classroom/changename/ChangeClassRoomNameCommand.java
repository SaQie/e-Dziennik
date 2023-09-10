package pl.edziennik.application.command.classroom.changename;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import pl.edziennik.application.common.dispatcher.Command;
import pl.edziennik.application.common.dispatcher.Handler;
import pl.edziennik.common.valueobject.id.ClassRoomId;
import pl.edziennik.common.valueobject.vo.ClassRoomName;

/**
 * A command used for changing the specific classroom name
 */
@Handler(handler = ChangeClassRoomNameCommandHandler.class, validator = ChangeClassRoomNameCommandValidator.class)
public record ChangeClassRoomNameCommand(

        ClassRoomId classRoomId,
        @Valid @NotNull(message = "${field.empty}") ClassRoomName classRoomName

) implements Command {

    public static final String CLASS_ROOM_ID = "classRoomId";
    public static final String CLASS_ROOM_NAME = "classRoomName";

}
