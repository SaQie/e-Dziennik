package pl.edziennik.application.command.classroomschedule.create;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.command.lessonplan.create.CreateLessonPlanCommand;
import pl.edziennik.application.common.dispatcher.ValidationErrorBuilder;
import pl.edziennik.application.common.dispatcher.base.IBaseValidator;
import pl.edziennik.common.valueobject.vo.Description;
import pl.edziennik.domain.classroom.ClassRoomSchedule;
import pl.edziennik.infrastructure.repository.classroom.ClassRoomCommandRepository;
import pl.edziennik.infrastructure.repository.classroomschedule.ClassRoomScheduleCommandRepository;
import pl.edziennik.infrastructure.validator.errorcode.ErrorCode;

import java.util.List;

@Component
@AllArgsConstructor
class CreateClassRoomScheduleCommandValidator implements IBaseValidator<CreateClassRoomScheduleCommand> {

    public static final String BUSY_CLASS_ROOM_SCHEDULE_MESSAGE_KEY = "classroom.schedule.busy";

    private final ClassRoomCommandRepository classRoomCommandRepository;
    private final ClassRoomScheduleCommandRepository classRoomScheduleCommandRepository;

    @Override
    public void validate(CreateClassRoomScheduleCommand command, ValidationErrorBuilder errorBuilder) {
        checkClassRoomExists(command, errorBuilder);
        checkClassRoomScheduleConflicts(command, errorBuilder);
    }

    /**
     * Check class-room exists
     */
    private void checkClassRoomExists(CreateClassRoomScheduleCommand command, ValidationErrorBuilder errorBuilder) {
        classRoomCommandRepository.findById(command.classRoomId())
                .orElseGet(() -> {
                    errorBuilder.addNotFoundError(CreateClassRoomScheduleCommand.CLASS_ROOM_ID);
                    return null;
                });
        errorBuilder.flush();
    }


    /**
     * Check that classroom's schedule doesn't conflict with provided time frame
     */
    private void checkClassRoomScheduleConflicts(CreateClassRoomScheduleCommand command, ValidationErrorBuilder errorBuilder) {
        List<ClassRoomSchedule> schedules = classRoomScheduleCommandRepository.getClassRoomSchedulesInTimeFrame(command.startDate(),
                command.endDate(), command.classRoomId());

        for (ClassRoomSchedule classRoomSchedule : schedules) {
            // If there exists any schedule conflicts with provided time frame
            Description description = classRoomSchedule.description()
                    .append(
                            classRoomSchedule.timeFrame().formattedStartDate() + " -> " + classRoomSchedule.timeFrame().formattedEndDate());

            errorBuilder.addError(
                    CreateLessonPlanCommand.CLASS_ROOM_ID,
                    BUSY_CLASS_ROOM_SCHEDULE_MESSAGE_KEY,
                    ErrorCode.BUSY_SCHEDULE,
                    description);
        }
    }
}
