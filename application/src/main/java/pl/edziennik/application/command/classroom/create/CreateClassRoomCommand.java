package pl.edziennik.application.command.classroom.create;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.application.common.dispatcher.HandledBy;
import pl.edziennik.application.common.dispatcher.ValidatedBy;
import pl.edziennik.application.common.dispatcher.ICommand;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.valueobject.vo.ClassRoomName;

/**
 * A command used for creating a new classroom
 */
@HandledBy(handler = CreateClassRoomCommandHandler.class)
@ValidatedBy(validator = CreateClassRoomCommandValidator.class)
public record CreateClassRoomCommand(

        @JsonIgnore SchoolId schoolId,
        @Valid @NotNull(message = "${field.empty}") ClassRoomName classRoomName

) implements ICommand<OperationResult> {

    public static final String CLASS_ROOM_NAME = "classRoomName";
    public static final String SCHOOL_ID = "schoolId";

    public CreateClassRoomCommand(SchoolId schoolId, CreateClassRoomCommand command) {
        this(schoolId, command.classRoomName);
    }
}
