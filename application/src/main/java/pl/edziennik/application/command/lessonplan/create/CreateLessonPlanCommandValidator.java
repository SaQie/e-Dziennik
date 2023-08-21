package pl.edziennik.application.command.lessonplan.create;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.ValidationErrorBuilder;
import pl.edziennik.application.common.dispatcher.base.IBaseValidator;
import pl.edziennik.common.valueobject.id.TeacherId;
import pl.edziennik.common.valueobject.vo.Description;
import pl.edziennik.domain.subject.Subject;
import pl.edziennik.infrastructure.repository.classroom.ClassRoomCommandRepository;
import pl.edziennik.infrastructure.repository.classroomschedule.ClassRoomScheduleCommandRepository;
import pl.edziennik.infrastructure.repository.schoolclass.SchoolClassCommandRepository;
import pl.edziennik.infrastructure.repository.subject.SubjectCommandRepository;
import pl.edziennik.infrastructure.repository.teacher.TeacherCommandRepository;
import pl.edziennik.infrastructure.repository.teacherschedule.TeacherScheduleCommandRepository;
import pl.edziennik.infrastructure.validator.errorcode.ErrorCode;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
class CreateLessonPlanCommandValidator implements IBaseValidator<CreateLessonPlanCommand> {

    public static final String BUSY_TEACHER_SCHEDULE_MESSAGE_KEY = "teacher.schedule.busy";
    public static final String BUSY_CLASS_ROOM_SCHEDULE_MESSAGE_KEY = "classroom.schedule.busy";

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
     * Check that teacher's schedule doesn't conflict with provided time frame
     */
    private void checkTeacherScheduleConflicts(CreateLessonPlanCommand command, ValidationErrorBuilder errorBuilder) {
        TeacherId teacherId = command.teacherId();
        if (teacherId == null) {
            // If teacher is not provided (we should hint teacher from subject)
            Subject subject = subjectCommandRepository.getBySubjectId(command.subjectId());
            teacherId = subject.teacher().teacherId();
        }

        List<Description> schedules = teacherScheduleCommandRepository.getTeacherSchedulesInTimeFrame(command.startDate(), command.endDate(), teacherId);

        if (!schedules.isEmpty()) {
            // If there exist any schedule conflicts with provided time frame
            String description = schedules.stream()
                    .map(Description::value)
                    .collect(Collectors.joining(" -- "));

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
        List<Description> schedules = classRoomScheduleCommandRepository.getClassRoomSchedulesInTimeFrame(command.startDate(),
                command.endDate(), command.classRoomId());

        if (!schedules.isEmpty()) {
            // If there exists any schedule conflicts with provided time frame
            String description = schedules.stream()
                    .map(Description::value)
                    .collect(Collectors.joining(" -- "));

            errorBuilder.addError(
                    CreateLessonPlanCommand.CLASS_ROOM_ID,
                    BUSY_CLASS_ROOM_SCHEDULE_MESSAGE_KEY,
                    ErrorCode.BUSY_SCHEDULE,
                    description);
        }
    }
}
