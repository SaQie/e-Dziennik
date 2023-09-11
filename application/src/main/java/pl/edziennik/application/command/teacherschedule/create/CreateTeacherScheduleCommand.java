package pl.edziennik.application.command.teacherschedule.create;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import pl.edziennik.application.common.dispatcher.Command;
import pl.edziennik.application.common.dispatcher.Handler;
import pl.edziennik.common.valueobject.id.TeacherId;
import pl.edziennik.common.valueobject.id.TeacherScheduleId;
import pl.edziennik.common.valueobject.vo.Description;

import java.time.LocalDateTime;

/**
 * A command used for add something on teacher schedule
 */
@Handler(handler = CreateTeacherScheduleCommandHandler.class, validator = CreateTeacherScheduleCommandValidator.class)
public record CreateTeacherScheduleCommand(

        @JsonIgnore TeacherScheduleId teacherScheduleId,
        @JsonIgnore TeacherId teacherId,
        @Valid @NotNull(message = "{field.empty}") Description description,
        @Valid @NotNull(message = "{field.empty}") @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startDate,
        @Valid @NotNull(message = "{field.empty}") @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endDate

) implements Command {

    public static final String TEACHER_ID = "teacherId";
    public static final String DESCRIPTION = "description";
    public static final String START_DATE = "startDate";
    public static final String END_DATE = "endDate";

    @JsonCreator
    public CreateTeacherScheduleCommand(TeacherId teacherId, Description description,
                                        LocalDateTime startDate, LocalDateTime endDate) {
        this(TeacherScheduleId.create(), teacherId, description, startDate, endDate);
    }

    public CreateTeacherScheduleCommand(TeacherId teacherId, CreateTeacherScheduleCommand command) {
        this(TeacherScheduleId.create(), teacherId, command.description, command.startDate, command.endDate);
    }
}
