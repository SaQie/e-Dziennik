package pl.edziennik.application.command.classroom.create;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import pl.edziennik.application.common.dispatcher.Command;
import pl.edziennik.application.common.dispatcher.Handler;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.valueobject.vo.ClassRoomName;

/**
 * A command used for creating a new classroom
 */
@Handler(handler = CreateClassRoomCommandHandler.class, validator = CreateClassRoomCommandValidator.class)
public record CreateClassRoomCommand(

        @JsonIgnore SchoolId schoolId,
        @Valid @NotNull(message = "${field.empty}") ClassRoomName classRoomName

) implements Command {

    public static final String CLASS_ROOM_NAME = "classRoomName";
    public static final String SCHOOL_ID = "schoolId";

    public CreateClassRoomCommand(SchoolId schoolId, CreateClassRoomCommand command) {
        this(schoolId, command.classRoomName);
    }
}
