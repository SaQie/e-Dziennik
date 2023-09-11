package pl.edziennik.application.command.lessonplan.create;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import pl.edziennik.application.common.dispatcher.Command;
import pl.edziennik.application.common.dispatcher.Handler;
import pl.edziennik.common.valueobject.id.*;

import java.time.LocalDateTime;

/**
 * A command used for creating new lesson plan
 */
@Handler(handler = CreateLessonPlanCommandHandler.class, validator = CreateLessonPlanCommandValidator.class)
public record CreateLessonPlanCommand(

        @JsonIgnore LessonPlanId lessonPlanId,
        @Valid @NotNull(message = "{subject.empty}") SubjectId subjectId,
        TeacherId teacherId,
        @Valid @NotNull(message = "{field.empty}") ClassRoomId classRoomId,
        @Valid @NotNull(message = "{field.empty}") @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startDate,
        @Valid @NotNull(message = "{field.empty}") @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endDate,
        @JsonIgnore SchoolClassId schoolClassId

) implements Command {

    public static final String SUBJECT_ID = "subjectId";
    public static final String CLASS_ROOM_ID = "classRoomId";
    public static final String START_DATE = "startDate";
    public static final String END_DATE = "endDate";
    public static final String SCHOOL_CLASS_ID = "schoolClassId";
    public static final String TEACHER_ID = "teacherId";

    @JsonCreator
    public CreateLessonPlanCommand(SubjectId subjectId, TeacherId teacherId, ClassRoomId classRoomId, LocalDateTime startDate,
                                   LocalDateTime endDate, SchoolClassId schoolClassId) {
        this(LessonPlanId.create(), subjectId, teacherId, classRoomId, startDate, endDate, schoolClassId);
    }

    public CreateLessonPlanCommand(SchoolClassId schoolClassId, CreateLessonPlanCommand command) {
        this(LessonPlanId.create(), command.subjectId, command.teacherId, command.classRoomId, command.startDate, command.endDate, schoolClassId);
    }

}
