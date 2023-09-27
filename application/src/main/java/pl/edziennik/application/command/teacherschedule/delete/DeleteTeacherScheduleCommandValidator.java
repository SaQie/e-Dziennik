package pl.edziennik.application.command.teacherschedule.delete;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.ValidationErrorBuilder;
import pl.edziennik.application.common.dispatcher.Validator;
import pl.edziennik.common.valueobject.vo.TimeFrame;
import pl.edziennik.domain.teacher.TeacherSchedule;
import pl.edziennik.infrastructure.repository.teacherschedule.TeacherScheduleCommandRepository;
import pl.edziennik.infrastructure.validator.errorcode.ErrorCode;

import java.time.LocalDateTime;

@Component
@AllArgsConstructor
class DeleteTeacherScheduleCommandValidator implements Validator<DeleteTeacherScheduleCommand> {

    public static final String TEACHER_SCHEDULE_IS_LINKED_TO_LESSON_PLAN = "teacher.schedule.is.linked.to.lesson.plan";
    public static final String CANNOT_DELETE_PAST_TEACHER_SCHEDULES = "cannot.delete.past.teacher.schedules";
    public static final String CANNOT_DELETE_TEACHER_SCHEDULE_IN_PROGRESS = "cannot.delete.teacher.schedule.in.progress";

    private final TeacherScheduleCommandRepository teacherScheduleCommandRepository;

    @Override
    public void validate(DeleteTeacherScheduleCommand command, ValidationErrorBuilder errorBuilder) {
        checkTeacherScheduleExists(command, errorBuilder);
        checkLessonPlanExists(command, errorBuilder);
        checkTeacherScheduleTimeFrame(command, errorBuilder);
    }


    private void checkTeacherScheduleTimeFrame(DeleteTeacherScheduleCommand command, ValidationErrorBuilder errorBuilder) {
        TeacherSchedule teacherSchedule = teacherScheduleCommandRepository.getReferenceById(command.teacherScheduleId());
        TimeFrame timeFrame = teacherSchedule.timeFrame();

        // If try to delete schedule from past
        boolean isTimeFrameInPast = timeFrame.startDate().isBefore(LocalDateTime.now()) && timeFrame.endDate().isBefore(LocalDateTime.now());

        if (isTimeFrameInPast) {
            errorBuilder.addError(
                    DeleteTeacherScheduleCommand.TEACHER_SCHEDULE_ID,
                    CANNOT_DELETE_PAST_TEACHER_SCHEDULES,
                    ErrorCode.DATE_CONFLICT
            );
        }

        // if try to delete schedule that is actually in progress
        boolean isActualTimeFrame = timeFrame.startDate().isBefore(LocalDateTime.now()) && timeFrame.endDate().isAfter(LocalDateTime.now());

        if (isActualTimeFrame) {
            errorBuilder.addError(
                    DeleteTeacherScheduleCommand.TEACHER_SCHEDULE_ID,
                    CANNOT_DELETE_TEACHER_SCHEDULE_IN_PROGRESS,
                    ErrorCode.DATE_CONFLICT
            );
        }
    }

    /**
     * Check, if teacher schedule is linked to lesson plan
     */
    private void checkLessonPlanExists(DeleteTeacherScheduleCommand command, ValidationErrorBuilder errorBuilder) {
        TeacherSchedule teacherSchedule = teacherScheduleCommandRepository.getReferenceById(command.teacherScheduleId());
        if (teacherSchedule.isLinkedWithLessonPlan()) {
            errorBuilder.addError(
                    DeleteTeacherScheduleCommand.TEACHER_SCHEDULE_ID,
                    TEACHER_SCHEDULE_IS_LINKED_TO_LESSON_PLAN,
                    ErrorCode.RELEATED_OBJECT_EXISTS
            );
        }
    }

    /**
     * Check teacher schedule with given id exists
     */
    private void checkTeacherScheduleExists(DeleteTeacherScheduleCommand command, ValidationErrorBuilder errorBuilder) {
        teacherScheduleCommandRepository.findById(command.teacherScheduleId())
                .orElseGet(() -> {
                    errorBuilder.addNotFoundError(DeleteTeacherScheduleCommand.TEACHER_SCHEDULE_ID);
                    return null;
                });
        errorBuilder.flush();
    }
}
