package pl.edziennik.application.command.classroom.changename;

import io.swagger.v3.oas.annotations.media.Schema;
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
        @Schema(example = "1B")
        @Valid @NotNull(message = "${field.empty}") ClassRoomName classRoomName

) implements Command {

    public static final String CLASS_ROOM_ID = "classRoomId";
    public static final String CLASS_ROOM_NAME = "classRoomName";

}
