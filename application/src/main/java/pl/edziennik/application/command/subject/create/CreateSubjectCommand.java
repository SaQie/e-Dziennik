package pl.edziennik.application.command.subject.create;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.application.common.dispatcher.base.HandledBy;
import pl.edziennik.application.common.dispatcher.base.ValidatedBy;
import pl.edziennik.application.common.dispatcher.command.ICommand;
import pl.edziennik.common.valueobject.id.SchoolClassId;
import pl.edziennik.common.valueobject.id.TeacherId;
import pl.edziennik.common.valueobject.vo.Description;
import pl.edziennik.common.valueobject.vo.Name;

/**
 * A command used for creating a new subject in a specific school class
 */
@HandledBy(handler = CreateSubjectCommandHandler.class)
@ValidatedBy(validator = CreateSubjectCommandValidator.class)
public record CreateSubjectCommand(

        @Valid @NotNull(message = "{name.empty}") Name name,

        Description description,

        @NotNull(message = "{teacher.empty}") TeacherId teacherId,

        SchoolClassId schoolClassId


) implements ICommand<OperationResult> {

    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String TEACHER_ID = "teacherId";
    public static final String SCHOOL_CLASS_ID = "schoolClassId";

    public CreateSubjectCommand(SchoolClassId schoolClassId, CreateSubjectCommand command) {
        this(command.name, command.description, command.teacherId, schoolClassId);
    }
}
