package pl.edziennik.application.command.lessonplan.create;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.ValidationErrorBuilder;
import pl.edziennik.application.common.dispatcher.base.IBaseValidator;
import pl.edziennik.common.valueobject.id.TeacherId;
import pl.edziennik.common.valueobject.vo.Description;
import pl.edziennik.common.valueobject.vo.TimeFrame;
import pl.edziennik.common.valueobject.vo.TimeFrameDuration;
import pl.edziennik.domain.classroom.ClassRoom;
import pl.edziennik.domain.classroom.ClassRoomSchedule;
import pl.edziennik.domain.school.School;
import pl.edziennik.domain.schoolclass.SchoolClass;
import pl.edziennik.domain.subject.Subject;
import pl.edziennik.domain.teacher.TeacherSchedule;
import pl.edziennik.infrastructure.repository.classroom.ClassRoomCommandRepository;
import pl.edziennik.infrastructure.repository.classroomschedule.ClassRoomScheduleCommandRepository;
import pl.edziennik.infrastructure.repository.schoolclass.SchoolClassCommandRepository;
import pl.edziennik.infrastructure.repository.subject.SubjectCommandRepository;
import pl.edziennik.infrastructure.repository.teacher.TeacherCommandRepository;
import pl.edziennik.infrastructure.repository.teacherschedule.TeacherScheduleCommandRepository;
import pl.edziennik.infrastructure.validator.errorcode.ErrorCode;

import java.util.List;

@Component
@AllArgsConstructor
class CreateLessonPlanCommandValidator implements IBaseValidator<CreateLessonPlanCommand> {

    public static final String BUSY_TEACHER_SCHEDULE_MESSAGE_KEY = "teacher.schedule.busy";
    public static final String BUSY_CLASS_ROOM_SCHEDULE_MESSAGE_KEY = "classroom.schedule.busy";
    public static final String LESSON_TIME_GREATER_THAN_CONFIGURATION_LIMIT = "lesson.time.greater.than.config.limit";
    public static final String END_DATE_CANNOT_BE_BEFORE_START_DATE = "end.date.cannot.be.before.start.date";
    public static final String SCHEDULE_TIME_TOO_SHORT_MESSAGE_KEY = "schedule.time.too.short";

    private final TeacherCommandRepository teacherCommandRepository;
    private final SubjectCommandRepository subjectCommandRepository;
    private final ClassRoomCommandRepository classRoomCommandRepository;
    private final SchoolClassCommandRepository schoolClassCommandRepository;
    private final TeacherScheduleCommandRepository teacherScheduleCommandRepository;
    private final ClassRoomScheduleCommandRepository classRoomScheduleCommandRepository;

    @Override
    public void validate(CreateLessonPlanCommand command, ValidationErrorBuilder errorBuilder) {
        checkSubjectExists(command, errorBuilder);
        checkTeacherExists(command, errorBuilder);
        checkClassRoomExists(command, errorBuilder);
        checkSchoolClassExists(command, errorBuilder);

        errorBuilder.flush();

        checkEndDateIsNotBeforeStartDate(command, errorBuilder);
        checkSchoolConfigurationLessonTime(command, errorBuilder);
        checkMinimalScheduleTimeFromSchoolConfiguration(command, errorBuilder);
        checkTeacherScheduleConflicts(command, errorBuilder);
        checkClassRoomScheduleConflicts(command, errorBuilder);

    }

    /**
     * Check subject exists
     */
    private void checkSubjectExists(CreateLessonPlanCommand command, ValidationErrorBuilder errorBuilder) {
        subjectCommandRepository.findById(command.subjectId())
                .orElseGet(() -> {
                    errorBuilder.addNotFoundError(CreateLessonPlanCommand.SUBJECT_ID);
                    return null;
                });
    }

    /**
     * Check provided teacher exists
     */
    private void checkTeacherExists(CreateLessonPlanCommand command, ValidationErrorBuilder errorBuilder) {
        if (command.teacherId() != null) {
            teacherCommandRepository.findById(command.teacherId())
                    .orElseGet(() -> {
                        errorBuilder.addNotFoundError(CreateLessonPlanCommand.TEACHER_ID);
                        return null;
                    });
        }
    }

    /**
     * Check class-room exists
     */
    private void checkClassRoomExists(CreateLessonPlanCommand command, ValidationErrorBuilder errorBuilder) {
        classRoomCommandRepository.findById(command.classRoomId())
                .orElseGet(() -> {
                    errorBuilder.addNotFoundError(CreateLessonPlanCommand.CLASS_ROOM_ID);
                    return null;
                });
    }

    /**
     * Check school class exists
     */
    private void checkSchoolClassExists(CreateLessonPlanCommand command, ValidationErrorBuilder errorBuilder) {
        schoolClassCommandRepository.findById(command.schoolClassId())
                .orElseGet(() -> {
                    errorBuilder.addNotFoundError(CreateLessonPlanCommand.SCHOOL_CLASS_ID);
                    return null;
                });
    }

    /**
     * Check end date isn't before start date
     */
    private void checkEndDateIsNotBeforeStartDate(CreateLessonPlanCommand command, ValidationErrorBuilder errorBuilder) {
        TimeFrame timeFrame = TimeFrame.of(command.startDate(), command.endDate());
        if (command.endDate().isBefore(command.startDate())) {
            errorBuilder.addError(
                    CreateLessonPlanCommand.END_DATE,
                    END_DATE_CANNOT_BE_BEFORE_START_DATE,
                    ErrorCode.DATE_CONFLICT,
                    timeFrame.formattedEndDate(), timeFrame.formattedStartDate());
        }
    }


    /**
     * Check lesson time is not greater than lesson time in school config
     */
    private void checkSchoolConfigurationLessonTime(CreateLessonPlanCommand command, ValidationErrorBuilder errorBuilder) {
        SchoolClass schoolClass = schoolClassCommandRepository.getBySchoolClassId(command.schoolClassId());
        School school = schoolClass.school();
        TimeFrameDuration maxLessonTime = school.schoolConfiguration().maxLessonTime();
        TimeFrame timeFrame = TimeFrame.of(command.startDate(), command.endDate());

        if (timeFrame.duration().isGreaterThan(maxLessonTime)) {
            errorBuilder.addError(
                    CreateLessonPlanCommand.END_DATE,
                    LESSON_TIME_GREATER_THAN_CONFIGURATION_LIMIT,
                    ErrorCode.CONFIGURATION_CONFLICT,
                    maxLessonTime, timeFrame.duration());
        }


    }

    /**
     * Check that teacher's schedule doesn't conflict with provided time frame
     */
    private void checkTeacherScheduleConflicts(CreateLessonPlanCommand command, ValidationErrorBuilder errorBuilder) {
        TeacherId teacherId = command.teacherId();
        if (teacherId == null) {
            // If teacher is not provided (we should hint teacher from subject)
            Subject subject = subjectCommandRepository.getBySubjectId(command.subjectId());
            teacherId = subject.teacher().teacherId();
        }

        List<TeacherSchedule> schedules = teacherScheduleCommandRepository.getTeacherSchedulesInTimeFrame(command.startDate(), command.endDate(), teacherId);

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

    /**
     * Check that classroom's schedule doesn't conflict with provided time frame
     */
    private void checkClassRoomScheduleConflicts(CreateLessonPlanCommand command, ValidationErrorBuilder errorBuilder) {
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


    /**
     * Check given time frame is not smaller than school config minimal schedule time
     */
    private void checkMinimalScheduleTimeFromSchoolConfiguration(CreateLessonPlanCommand command, ValidationErrorBuilder errorBuilder) {
        TimeFrame timeFrame = TimeFrame.of(command.startDate(), command.endDate());
        ClassRoom classRoom = classRoomCommandRepository.getById(command.classRoomId());
        TimeFrameDuration minScheduleTime = classRoom.school().schoolConfiguration().minScheduleTime();

        if (timeFrame.duration().isSmallerThan(minScheduleTime)) {
            errorBuilder.addError(
                    CreateLessonPlanCommand.START_DATE,
                    SCHEDULE_TIME_TOO_SHORT_MESSAGE_KEY,
                    ErrorCode.DATE_CONFLICT,
                    minScheduleTime);
        }
    }

}
