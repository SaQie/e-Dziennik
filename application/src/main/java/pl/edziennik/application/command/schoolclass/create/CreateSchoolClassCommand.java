package pl.edziennik.application.command.schoolclass.create;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.application.common.dispatcher.base.HandledBy;
import pl.edziennik.application.common.dispatcher.base.ValidatedBy;
import pl.edziennik.application.common.dispatcher.command.ICommand;
import pl.edziennik.common.valueobject.Name;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.valueobject.id.TeacherId;

/**
 *  A command used for creating a new school class
 */
@HandledBy(handler = CreateSchoolClassCommandHandler.class)
@ValidatedBy(validator = CreateSchoolClassCommandValidator.class)
public record CreateSchoolClassCommand(

        @Valid @NotNull(message = "{name.empty}") Name className,
        @NotNull(message = "{teacher.empty}") TeacherId teacherId,
        @NotNull(message = "{school.empty}") SchoolId schoolId

) implements ICommand<OperationResult> {

    public static final String CLASS_NAME = "className";
    public static final String TEACHER_ID = "teacherId";
    public static final String SCHOOL_ID = "schoolId";


}
