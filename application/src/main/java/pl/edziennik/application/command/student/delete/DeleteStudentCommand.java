package pl.edziennik.application.command.student.delete;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import pl.edziennik.application.common.dispatcher.Command;
import pl.edziennik.application.common.dispatcher.Handler;
import pl.edziennik.common.valueobject.id.StudentId;

/**
 * A command used for deleting the existing user account
 */
@Handler(handler = DeleteStudentCommandHandler.class)
public record DeleteStudentCommand(

        @JsonIgnore
        @NotNull(message = "{student.empty}") StudentId studentId

) implements Command {

    public static final String STUDENT_ID = "studentId";

}
