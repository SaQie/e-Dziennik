package pl.edziennik.application.command.teacherschedule.create;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.application.common.dispatcher.HandledBy;
import pl.edziennik.application.common.dispatcher.ValidatedBy;
import pl.edziennik.application.common.dispatcher.ICommand;
import pl.edziennik.common.valueobject.id.TeacherId;
import pl.edziennik.common.valueobject.vo.Description;

import java.time.LocalDateTime;

/**
 * A command used for add something on teacher schedule
 */
@HandledBy(handler = CreateTeacherScheduleCommandHandler.class)
@ValidatedBy(validator = CreateTeacherScheduleCommandValidator.class)
public record CreateTeacherScheduleCommand(

        @JsonIgnore TeacherId teacherId,
        @Valid @NotNull(message = "{field.empty}") Description description,
        @Valid @NotNull(message = "{field.empty}") @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startDate,
        @Valid @NotNull(message = "{field.empty}") @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endDate

) implements ICommand<OperationResult> {

    public static final String TEACHER_ID = "teacherId";
    public static final String DESCRIPTION = "description";
    public static final String START_DATE = "startDate";
    public static final String END_DATE = "endDate";

    public CreateTeacherScheduleCommand(TeacherId teacherId, CreateTeacherScheduleCommand command) {
        this(teacherId, command.description, command.startDate, command.endDate);
    }
}
