package pl.edziennik.application.command.classroomschedule.delete;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.ValidationErrorBuilder;
import pl.edziennik.application.common.dispatcher.Validator;
import pl.edziennik.common.valueobject.vo.TimeFrame;
import pl.edziennik.domain.classroom.ClassRoomSchedule;
import pl.edziennik.infrastructure.repository.classroomschedule.ClassRoomScheduleCommandRepository;
import pl.edziennik.infrastructure.validator.errorcode.ErrorCode;

import java.time.LocalDateTime;

@Component
@AllArgsConstructor
class DeleteClassRoomScheduleCommandValidator implements Validator<DeleteClassRoomScheduleCommand> {

    public static final String CANNOT_DELETE_PAST_CLASS_ROOM_SCHEDULES = "cannot.delete.past.class.room.schedules";
    public static final String CANNOT_DELETE_CLASS_ROOM_SCHEDULES_IN_PROGRESS = "cannot.delete.class.room.schedules.in.progress";
    public static final String CLASS_ROOM_SCHEDULE_IS_LINKED_TO_LESSON_PLAN = "class.room.schedule.is.linked.to.lesson.plan";


    private final ClassRoomScheduleCommandRepository classRoomScheduleCommandRepository;

    @Override
    public void validate(DeleteClassRoomScheduleCommand command, ValidationErrorBuilder errorBuilder) {
        checkClassRoomScheduleExists(command, errorBuilder);
        checkLessonPlanExists(command, errorBuilder);
        checkClassRoomScheduleTimeFrame(command, errorBuilder);
    }

    /**
     * Check if class room schedule exists
     */
    private void checkClassRoomScheduleExists(DeleteClassRoomScheduleCommand command, ValidationErrorBuilder errorBuilder) {
        classRoomScheduleCommandRepository.findById(command.classRoomScheduleId())
                .orElseGet(() -> {
                    errorBuilder.addNotFoundError(DeleteClassRoomScheduleCommand.CLASS_ROOM_SCHEDULE_ID);
                    return null;
                });
        errorBuilder.flush();
    }

    /**
     * Check if class room schedule is linked with lesson plan
     */
    private void checkLessonPlanExists(DeleteClassRoomScheduleCommand command, ValidationErrorBuilder errorBuilder) {
        ClassRoomSchedule classRoomSchedule = classRoomScheduleCommandRepository.getReferenceById(command.classRoomScheduleId());
        boolean isLinkedWithLessonPlan = classRoomSchedule.isLinkedWithLessonPlan();

        if (isLinkedWithLessonPlan){
            errorBuilder.addError(
                    DeleteClassRoomScheduleCommand.CLASS_ROOM_SCHEDULE_ID,
                    CLASS_ROOM_SCHEDULE_IS_LINKED_TO_LESSON_PLAN,
                    ErrorCode.RELEATED_OBJECT_EXISTS
            );
        }

    }

    /**
     * Check if class room schedule time frame is from past or is currently in progress
     */
    private void checkClassRoomScheduleTimeFrame(DeleteClassRoomScheduleCommand command, ValidationErrorBuilder errorBuilder) {
        ClassRoomSchedule classRoomSchedule = classRoomScheduleCommandRepository.getReferenceById(command.classRoomScheduleId());
        TimeFrame timeFrame = classRoomSchedule.timeFrame();

        // If try to delete schedule from past
        boolean isTimeFrameInPast = timeFrame.startDate().isBefore(LocalDateTime.now()) && timeFrame.endDate().isBefore(LocalDateTime.now());

        if (isTimeFrameInPast) {
            errorBuilder.addError(
                    DeleteClassRoomScheduleCommand.CLASS_ROOM_SCHEDULE_ID,
                    CANNOT_DELETE_PAST_CLASS_ROOM_SCHEDULES,
                    ErrorCode.DATE_CONFLICT
            );
        }

        // if try to delete schedule that is actually in progress
        boolean isActualTimeFrame = timeFrame.startDate().isBefore(LocalDateTime.now()) && timeFrame.endDate().isAfter(LocalDateTime.now());

        if (isActualTimeFrame) {
            errorBuilder.addError(
                    DeleteClassRoomScheduleCommand.CLASS_ROOM_SCHEDULE_ID,
                    CANNOT_DELETE_CLASS_ROOM_SCHEDULES_IN_PROGRESS,
                    ErrorCode.DATE_CONFLICT
            );
        }
    }
}
