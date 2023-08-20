package pl.edziennik.application.command.classroom.create;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.application.common.dispatcher.base.HandledBy;
import pl.edziennik.application.common.dispatcher.base.ValidatedBy;
import pl.edziennik.application.common.dispatcher.command.ICommand;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.valueobject.vo.ClassRoomName;

/**
 * A command used for creating a new classroom
 */
@HandledBy(handler = CreateClassRoomCommandHandler.class)
@ValidatedBy(validator = CreateClassRoomCommandValidator.class)
public record CreateClassRoomCommand(

        SchoolId schoolId,
        @Valid @NotNull(message = "${field.empty}") ClassRoomName classRoomName

) implements ICommand<OperationResult> {

    public static final String CLASS_ROOM_NAME = "classRoomName";
    public static final String SCHOOL_ID = "schoolId";

}
