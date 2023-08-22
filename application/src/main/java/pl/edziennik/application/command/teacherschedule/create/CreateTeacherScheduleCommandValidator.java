package pl.edziennik.application.command.teacherschedule.create;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.command.lessonplan.create.CreateLessonPlanCommand;
import pl.edziennik.application.common.dispatcher.ValidationErrorBuilder;
import pl.edziennik.application.common.dispatcher.base.IBaseValidator;
import pl.edziennik.common.valueobject.vo.Description;
import pl.edziennik.domain.teacher.TeacherSchedule;
import pl.edziennik.infrastructure.repository.teacher.TeacherCommandRepository;
import pl.edziennik.infrastructure.repository.teacherschedule.TeacherScheduleCommandRepository;
import pl.edziennik.infrastructure.validator.errorcode.ErrorCode;

import java.util.List;

@Component
@AllArgsConstructor
class CreateTeacherScheduleCommandValidator implements IBaseValidator<CreateTeacherScheduleCommand> {

    public static final String BUSY_TEACHER_SCHEDULE_MESSAGE_KEY = "teacher.schedule.busy";

    private final TeacherCommandRepository teacherCommandRepository;
    private final TeacherScheduleCommandRepository teacherScheduleCommandRepository;


    @Override
    public void validate(CreateTeacherScheduleCommand command, ValidationErrorBuilder errorBuilder) {
        checkTeacherExists(command, errorBuilder);
        checkTeacherScheduleConflicts(command, errorBuilder);
    }

    /**
     * Check provided teacher exists
     */
    private void checkTeacherExists(CreateTeacherScheduleCommand command, ValidationErrorBuilder errorBuilder) {
        if (command.teacherId() != null) {
            teacherCommandRepository.findById(command.teacherId())
                    .orElseGet(() -> {
                        errorBuilder.addNotFoundError(CreateLessonPlanCommand.TEACHER_ID);
                        return null;
                    });
        }
    }

    /**
     * Check that teacher's schedule doesn't conflict with provided time frame
     */
    private void checkTeacherScheduleConflicts(CreateTeacherScheduleCommand command, ValidationErrorBuilder errorBuilder) {
        List<TeacherSchedule> schedules = teacherScheduleCommandRepository.getTeacherSchedulesInTimeFrame(command.startDate(),
                command.endDate(), command.teacherId());

        for (TeacherSchedule teacherSchedule : schedules) {
            // If there exist any schedule conflicts with provided time frame
            Description description = teacherSchedule.description()
                    .append(
                            teacherSchedule.timeFrame().formattedStartDate() + " -> " + teacherSchedule.timeFrame().formattedEndDate());


            errorBuilder.addError(
                    CreateLessonPlanCommand.TEACHER_ID,
                    BUSY_TEACHER_SCHEDULE_MESSAGE_KEY,
                    ErrorCode.BUSY_SCHEDULE,
                    description);
        }

    }
}
