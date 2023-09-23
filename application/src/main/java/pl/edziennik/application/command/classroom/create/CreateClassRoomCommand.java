package pl.edziennik.application.command.classroom.create;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import pl.edziennik.application.common.dispatcher.Command;
import pl.edziennik.application.common.dispatcher.Handler;
import pl.edziennik.common.valueobject.id.ClassRoomId;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.valueobject.vo.ClassRoomName;

/**
 * A command used for creating a new classroom
 */
@Handler(handler = CreateClassRoomCommandHandler.class, validator = CreateClassRoomCommandValidator.class)
public record CreateClassRoomCommand(

        @JsonIgnore ClassRoomId classRoomId,
        @JsonIgnore SchoolId schoolId,

        @Schema(example = "1B")
        @Valid @NotNull(message = "${field.empty}") ClassRoomName classRoomName

) implements Command {

    public static final String CLASS_ROOM_NAME = "classRoomName";
    public static final String SCHOOL_ID = "schoolId";
    public static final String CLASS_ROOM_ID = "classRoomId";


    @JsonCreator
    public CreateClassRoomCommand(SchoolId schoolId, ClassRoomName classRoomName) {
        this(ClassRoomId.create(), schoolId, classRoomName);
    }
}
