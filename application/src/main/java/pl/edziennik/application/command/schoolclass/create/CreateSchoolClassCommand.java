package pl.edziennik.application.command.schoolclass.create;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import pl.edziennik.application.common.dispatcher.Command;
import pl.edziennik.application.common.dispatcher.Handler;
import pl.edziennik.common.valueobject.id.SchoolClassId;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.valueobject.id.TeacherId;
import pl.edziennik.common.valueobject.vo.Name;

/**
 * A command used for creating a new school class
 */
@Handler(handler = CreateSchoolClassCommandHandler.class, validator = CreateSchoolClassCommandValidator.class)
public record CreateSchoolClassCommand(

        @JsonIgnore SchoolClassId schoolClassId,

        @Schema(example = "1B")
        @Valid @NotNull(message = "{name.empty}") Name className,

        @Schema(example = "29b8d0c2-5964-11ee-8c99-0242ac120002")
        @NotNull(message = "{teacher.empty}") TeacherId teacherId,

        @JsonIgnore SchoolId schoolId

) implements Command {

    public static final String CLASS_NAME = "className";
    public static final String TEACHER_ID = "teacherId";
    public static final String SCHOOL_ID = "schoolId";

    @JsonCreator
    public CreateSchoolClassCommand(Name className, TeacherId teacherId, SchoolId schoolId) {
        this(SchoolClassId.create(), className, teacherId, schoolId);
    }

    public CreateSchoolClassCommand(SchoolId schoolId, CreateSchoolClassCommand command) {
        this(SchoolClassId.create(), command.className, command.teacherId, schoolId);
    }
}
