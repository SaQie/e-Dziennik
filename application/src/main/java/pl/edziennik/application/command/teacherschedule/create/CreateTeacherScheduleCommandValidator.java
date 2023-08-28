package pl.edziennik.application.command.teacherschedule.create;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.command.lessonplan.create.CreateLessonPlanCommand;
import pl.edziennik.application.common.dispatcher.ValidationErrorBuilder;
import pl.edziennik.application.common.dispatcher.base.IBaseValidator;
import pl.edziennik.common.valueobject.vo.Description;
import pl.edziennik.common.valueobject.vo.TimeFrame;
import pl.edziennik.common.valueobject.vo.TimeFrameDuration;
import pl.edziennik.domain.teacher.Teacher;
import pl.edziennik.domain.teacher.TeacherSchedule;
import pl.edziennik.infrastructure.repository.teacher.TeacherCommandRepository;
import pl.edziennik.infrastructure.repository.teacherschedule.TeacherScheduleCommandRepository;
import pl.edziennik.infrastructure.validator.errorcode.ErrorCode;

import java.util.List;

@Component
@AllArgsConstructor
class CreateTeacherScheduleCommandValidator implements IBaseValidator<CreateTeacherScheduleCommand> {

    public static final String BUSY_TEACHER_SCHEDULE_MESSAGE_KEY = "teacher.schedule.busy";
    public static final String END_DATE_CANNOT_BE_BEFORE_START_DATE = "end.date.cannot.be.before.start.date";
    public static final String SCHEDULE_TIME_TOO_SHORT = "schedule.time.too.short";

    private final TeacherCommandRepository teacherCommandRepository;
    private final TeacherScheduleCommandRepository teacherScheduleCommandRepository;


    @Override
    public void validate(CreateTeacherScheduleCommand command, ValidationErrorBuilder errorBuilder) {
        checkTeacherExists(command, errorBuilder);
        checkTeacherScheduleConflicts(command, errorBuilder);
        checkEndDateIsNotBeforeStartDate(command, errorBuilder);
        checkMinimalScheduleTimeFromSchoolConfiguration(command, errorBuilder);
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
            errorBuilder.flush();
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
                    CreateLessonPlanCommand.START_DATE,
                    BUSY_TEACHER_SCHEDULE_MESSAGE_KEY,
                    ErrorCode.BUSY_SCHEDULE,
                    description);
        }

    }

    /**
     * Check end date isn't before start date
     */
    private void checkEndDateIsNotBeforeStartDate(CreateTeacherScheduleCommand command, ValidationErrorBuilder errorBuilder) {
        TimeFrame timeFrame = TimeFrame.of(command.startDate(), command.endDate());
        if (command.endDate().isBefore(command.startDate())) {
            errorBuilder.addError(
                    CreateTeacherScheduleCommand.END_DATE,
                    END_DATE_CANNOT_BE_BEFORE_START_DATE,
                    ErrorCode.DATE_CONFLICT,
                    timeFrame.formattedEndDate(), timeFrame.formattedStartDate());
        }
    }

    /**
     * Check given time frame is not smaller than school config minimal schedule time
     */
    private void checkMinimalScheduleTimeFromSchoolConfiguration(CreateTeacherScheduleCommand command, ValidationErrorBuilder errorBuilder) {
        TimeFrame timeFrame = TimeFrame.of(command.startDate(), command.endDate());
        Teacher teacher = teacherCommandRepository.getByTeacherIdWithSchoolConfig(command.teacherId());
        TimeFrameDuration minScheduleTime = teacher.school().schoolConfiguration().minScheduleTime();

        if (timeFrame.duration().isSmallerThan(minScheduleTime)) {
            errorBuilder.addError(
                    CreateTeacherScheduleCommand.START_DATE,
                    SCHEDULE_TIME_TOO_SHORT,
                    ErrorCode.DATE_CONFLICT,
                    minScheduleTime);
        }
    }
}
