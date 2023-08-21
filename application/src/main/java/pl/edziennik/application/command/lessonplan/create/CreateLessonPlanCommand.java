package pl.edziennik.application.command.lessonplan.create;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.application.common.dispatcher.base.HandledBy;
import pl.edziennik.application.common.dispatcher.base.ValidatedBy;
import pl.edziennik.application.common.dispatcher.command.ICommand;
import pl.edziennik.common.valueobject.id.ClassRoomId;
import pl.edziennik.common.valueobject.id.SchoolClassId;
import pl.edziennik.common.valueobject.id.SubjectId;
import pl.edziennik.common.valueobject.id.TeacherId;

import java.time.LocalDateTime;

/**
 * A command used for creating new lesson plan
 */
@HandledBy(handler = CreateLessonPlanCommandHandler.class)
@ValidatedBy(validator = CreateLessonPlanCommandValidator.class)
public record CreateLessonPlanCommand(

        @Valid @NotNull(message = "{subject.empty}") SubjectId subjectId,
        TeacherId teacherId,
        @Valid @NotNull(message = "{field.empty}") ClassRoomId classRoomId,
        @Valid @NotNull(message = "{field.empty}") @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startDate,
        @Valid @NotNull(message = "{field.empty}") @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endDate,
        @JsonIgnore SchoolClassId schoolClassId

) implements ICommand<OperationResult> {

    public static final String SUBJECT_ID = "subjectId";
    public static final String CLASS_ROOM_ID = "classRoomId";
    public static final String START_DATE = "startDate";
    public static final String END_DATE = "endDate";
    public static final String SCHOOL_CLASS_ID = "schoolClassId";
    public static final String TEACHER_ID = "teacherId";


    public CreateLessonPlanCommand(SchoolClassId schoolClassId, CreateLessonPlanCommand command) {
        this(command.subjectId, command.teacherId, command.classRoomId, command.startDate, command.endDate, schoolClassId);
    }

}
