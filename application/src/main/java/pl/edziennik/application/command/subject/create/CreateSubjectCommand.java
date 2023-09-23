package pl.edziennik.application.command.subject.create;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import pl.edziennik.application.common.dispatcher.Command;
import pl.edziennik.application.common.dispatcher.Handler;
import pl.edziennik.common.valueobject.id.SchoolClassId;
import pl.edziennik.common.valueobject.id.SubjectId;
import pl.edziennik.common.valueobject.id.TeacherId;
import pl.edziennik.common.valueobject.vo.Description;
import pl.edziennik.common.valueobject.vo.Name;

/**
 * A command used for creating a new subject in a specific school class
 */
@Handler(handler = CreateSubjectCommandHandler.class, validator = CreateSubjectCommandValidator.class)
public record CreateSubjectCommand(

        @JsonIgnore SubjectId subjectId,

        @Schema(example = "Math")
        @Valid @NotNull(message = "{name.empty}") Name name,

        @Schema(example = "Learning math")
        Description description,

        @Schema(example = "395a1ca8-59e0-11ee-8c99-0242ac120002")
        @NotNull(message = "{teacher.empty}") TeacherId teacherId,
        @JsonIgnore SchoolClassId schoolClassId


) implements Command {

    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String TEACHER_ID = "teacherId";
    public static final String SCHOOL_CLASS_ID = "schoolClassId";

    @JsonCreator
    public CreateSubjectCommand(Name name, Description description, TeacherId teacherId, SchoolClassId schoolClassId) {
        this(SubjectId.create(), name, description, teacherId, schoolClassId);
    }

    public CreateSubjectCommand(SchoolClassId schoolClassId, CreateSubjectCommand command) {
        this(SubjectId.create(), command.name, command.description, command.teacherId, schoolClassId);
    }
}
