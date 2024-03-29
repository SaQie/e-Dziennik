package pl.edziennik.application.command.schoolclass.changeconfig;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import pl.edziennik.application.common.dispatcher.Command;
import pl.edziennik.application.common.dispatcher.Handler;
import pl.edziennik.common.valueobject.id.SchoolClassId;

/**
 * A command used for changing the existing school class configuration parameters
 * <br>
 * <b>maxStudentsSize</b> -> Student limit in the school class <br>
 * <b>autoAssignSubjects</b> -> Auto assign subjects(from the school class) to student while creating a new student account
 */
@Handler(handler = ChangeSchoolClassConfigurationValuesCommandHandler.class)
public record ChangeSchoolClassConfigurationValuesCommand(


        @JsonIgnore SchoolClassId schoolClassId,

        @Schema(example = "30")
        @NotNull(message = "{field.empty}")
        @Min(value = 1, message = "{max.students.size.error}")
        Integer maxStudentsSize,

        @Schema(example = "true")
        @NotNull(message = "{field.empty}") Boolean autoAssignSubjects


) implements Command {


    public static final String SCHOOL_CLASS_ID = "schoolClassId";
    public static final String MAX_STUDENTS_SIZE = "maxStudentsSize";
    public static final String AUTO_ASSIGN_SUBJECTS = "autoAssignSubjects";


}
