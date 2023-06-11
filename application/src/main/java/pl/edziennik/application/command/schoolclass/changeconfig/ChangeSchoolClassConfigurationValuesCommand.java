package pl.edziennik.application.command.schoolclass.changeconfig;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.application.common.dispatcher.base.HandledBy;
import pl.edziennik.application.common.dispatcher.command.ICommand;
import pl.edziennik.common.valueobject.id.SchoolClassId;

/**
 * A command used for changing the existing school class configuration parameters
 * <br>
 * <b>maxStudentsSize</b> -> Student limit in the school class <br>
 * <b>autoAssignSubjects</b> -> Auto assign subjects(from the school class) to student while creating a new student account
 */
@HandledBy(handler = ChangeSchoolClassConfigurationValuesCommandHandler.class)
public record ChangeSchoolClassConfigurationValuesCommand(

        SchoolClassId schoolClassId,

        @NotNull(message = "{field.empty}")
        @Min(value = 1, message = "{max.students.size.error}")
        Integer maxStudentsSize,

        @NotNull(message = "{field.empty}") Boolean autoAssignSubjects


) implements ICommand<OperationResult> {


    public static final String SCHOOL_CLASS_ID = "schoolClassId";
    public static final String MAX_STUDENTS_SIZE = "maxStudentsSize";
    public static final String AUTO_ASSIGN_SUBJECTS = "autoAssignSubjects";


}
