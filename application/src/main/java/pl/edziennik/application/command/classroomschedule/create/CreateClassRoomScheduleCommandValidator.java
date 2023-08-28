package pl.edziennik.application.command.classroomschedule.create;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.command.lessonplan.create.CreateLessonPlanCommand;
import pl.edziennik.application.common.dispatcher.ValidationErrorBuilder;
import pl.edziennik.application.common.dispatcher.base.IBaseValidator;
import pl.edziennik.common.valueobject.vo.Description;
import pl.edziennik.common.valueobject.vo.TimeFrame;
import pl.edziennik.common.valueobject.vo.TimeFrameDuration;
import pl.edziennik.domain.classroom.ClassRoom;
import pl.edziennik.domain.classroom.ClassRoomSchedule;
import pl.edziennik.infrastructure.repository.classroom.ClassRoomCommandRepository;
import pl.edziennik.infrastructure.repository.classroomschedule.ClassRoomScheduleCommandRepository;
import pl.edziennik.infrastructure.validator.errorcode.ErrorCode;

import java.util.List;

@Component
@AllArgsConstructor
class CreateClassRoomScheduleCommandValidator implements IBaseValidator<CreateClassRoomScheduleCommand> {

    public static final String BUSY_CLASS_ROOM_SCHEDULE_MESSAGE_KEY = "classroom.schedule.busy";
    public static final String END_DATE_CANNOT_BE_BEFORE_START_DATE = "end.date.cannot.be.before.start.date";
    public static final String SCHEDULE_TIME_TOO_SHORT_MESSAGE_KEY = "schedule.time.too.short";

    private final ClassRoomCommandRepository classRoomCommandRepository;
    private final ClassRoomScheduleCommandRepository classRoomScheduleCommandRepository;

    @Override
    public void validate(CreateClassRoomScheduleCommand command, ValidationErrorBuilder errorBuilder) {
        checkClassRoomExists(command, errorBuilder);
        checkClassRoomScheduleConflicts(command, errorBuilder);
        checkEndDateIsNotBeforeStartDate(command, errorBuilder);
        checkMinimalScheduleTimeFromSchoolConfiguration(command, errorBuilder);
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
                    CreateLessonPlanCommand.START_DATE,
                    BUSY_CLASS_ROOM_SCHEDULE_MESSAGE_KEY,
                    ErrorCode.BUSY_SCHEDULE,
                    description);
        }
    }

    /**
     * Check end date isn't before start date
     */
    private void checkEndDateIsNotBeforeStartDate(CreateClassRoomScheduleCommand command, ValidationErrorBuilder errorBuilder) {
        TimeFrame timeFrame = TimeFrame.of(command.startDate(), command.endDate());
        if (command.endDate().isBefore(command.startDate())) {
            errorBuilder.addError(
                    CreateClassRoomScheduleCommand.END_DATE,
                    END_DATE_CANNOT_BE_BEFORE_START_DATE,
                    ErrorCode.DATE_CONFLICT,
                    timeFrame.formattedEndDate(), timeFrame.formattedStartDate());
        }
    }

    /**
     * Check given time frame is not smaller than school config minimal schedule time
     */
    private void checkMinimalScheduleTimeFromSchoolConfiguration(CreateClassRoomScheduleCommand command, ValidationErrorBuilder errorBuilder) {
        TimeFrame timeFrame = TimeFrame.of(command.startDate(), command.endDate());
        ClassRoom classRoom = classRoomCommandRepository.getByIdWithSchoolConfig(command.classRoomId());
        TimeFrameDuration minScheduleTime = classRoom.school().schoolConfiguration().minScheduleTime();

        if (timeFrame.duration().isSmallerThan(minScheduleTime)) {
            errorBuilder.addError(
                    CreateClassRoomScheduleCommand.START_DATE,
                    SCHEDULE_TIME_TOO_SHORT_MESSAGE_KEY,
                    ErrorCode.DATE_CONFLICT,
                    minScheduleTime);
        }
    }

}
