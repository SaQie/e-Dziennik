package pl.edziennik.application.command.school.delete;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import pl.edziennik.application.common.dispatcher.Command;
import pl.edziennik.application.common.dispatcher.Handler;
import pl.edziennik.common.valueobject.id.SchoolId;

/**
 * A command used for deleting the existing school
 */
@Handler(handler = DeleteSchoolCommandHandler.class, validator = DeleteSchoolCommandValidator.class)
public record DeleteSchoolCommand(

        @Schema(example = "29b8d0c2-5964-11ee-8c99-0242ac120002")
        @NotNull(message = "{school.empty}") SchoolId schoolId

) implements Command {

    public static final String SCHOOL_ID = "schoolId";

}
