package pl.edziennik.application.command.classroom.changename;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.application.common.dispatcher.base.HandledBy;
import pl.edziennik.application.common.dispatcher.base.ValidatedBy;
import pl.edziennik.application.common.dispatcher.command.ICommand;
import pl.edziennik.common.valueobject.id.ClassRoomId;
import pl.edziennik.common.valueobject.vo.ClassRoomName;

/**
 * A command used for changing the specific classroom name
 */
@HandledBy(handler = ChangeClassRoomNameCommandHandler.class)
@ValidatedBy(validator = ChangeClassRoomCommandValidator.class)
public record ChangeClassRoomNameCommand(

        ClassRoomId classRoomId,
        @Valid @NotNull(message = "${field.empty}") ClassRoomName classRoomName

) implements ICommand<OperationResult> {

    public static final String CLASS_ROOM_ID = "classRoomId";
    public static final String CLASS_ROOM_NAME = "classRoomName";

}