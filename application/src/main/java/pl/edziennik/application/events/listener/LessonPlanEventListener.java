package pl.edziennik.application.events.listener;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import pl.edziennik.application.events.event.LessonPlanCreatedEvent;
import pl.edziennik.common.valueobject.id.ClassRoomScheduleId;
import pl.edziennik.common.valueobject.id.TeacherScheduleId;
import pl.edziennik.common.valueobject.vo.*;
import pl.edziennik.domain.classroom.ClassRoom;
import pl.edziennik.domain.classroom.ClassRoomSchedule;
import pl.edziennik.domain.lessonplan.LessonPlan;
import pl.edziennik.domain.subject.Subject;
import pl.edziennik.domain.teacher.Teacher;
import pl.edziennik.domain.teacher.TeacherSchedule;
import pl.edziennik.infrastructure.repository.classroomschedule.ClassRoomScheduleCommandRepository;
import pl.edziennik.infrastructure.repository.lessonplan.LessonPlanCommandRepository;
import pl.edziennik.infrastructure.repository.teacherschedule.TeacherScheduleCommandRepository;
import pl.edziennik.infrastructure.spring.ResourceCreator;

@Component
@AllArgsConstructor
public class LessonPlanEventListener {

    private static final String TEACHER_SCHEDULE_DESCRIPTION_MESSAGE_KEY = "teacher.schedule.description";
    private static final String CLASS_ROOM_SCHEDULE_DESCRIPTION_MESSAGE_KEY = "classroom.schedule.description";

    private final LessonPlanCommandRepository lessonPlanCommandRepository;
    private final TeacherScheduleCommandRepository teacherScheduleCommandRepository;
    private final ClassRoomScheduleCommandRepository classRoomScheduleCommandRepository;
    private final ResourceCreator res;

    @TransactionalEventListener(classes = LessonPlanCreatedEvent.class,
            phase = TransactionPhase.BEFORE_COMMIT)
    public void onLessonPlanCreatedEvent(LessonPlanCreatedEvent event) {
        LessonPlan lessonPlan = lessonPlanCommandRepository.getById(event.lessonPlanId());

        TimeFrame timeFrame = lessonPlan.timeFrame();
        ClassRoom classRoom = lessonPlan.classRoom();
        Teacher teacher = lessonPlan.teacher();
        Subject subject = lessonPlan.subject();
        FullName teacherFullName = teacher.personInformation().fullName();
        ClassRoomName classRoomName = classRoom.classRoomName();
        Name subjectName = subject.name();

        Description teacherScheduleDescription = createTeacherScheduleDescription(teacherFullName, classRoomName, subjectName, timeFrame);
        Description classRoomScheduleDescription = createClassRoomScheduleDescription(teacherFullName, classRoomName, subjectName, timeFrame);

        TeacherSchedule teacherSchedule = TeacherSchedule.builder()
                .teacherScheduleId(TeacherScheduleId.create())
                .timeFrame(timeFrame)
                .teacher(teacher)
                .description(teacherScheduleDescription)
                .build();

        ClassRoomSchedule classRoomSchedule = ClassRoomSchedule.builder()
                .classRoomScheduleId(ClassRoomScheduleId.create())
                .timeFrame(timeFrame)
                .classRoom(classRoom)
                .description(classRoomScheduleDescription)
                .build();

        teacherScheduleCommandRepository.save(teacherSchedule);
        classRoomScheduleCommandRepository.save(classRoomSchedule);

    }

    /**
     * Creates a description of teacher schedule based on lesson plan
     */
    private Description createTeacherScheduleDescription(FullName fullName, ClassRoomName classRoom, Name subjectName, TimeFrame timeFrame) {
        String description = res.of(TEACHER_SCHEDULE_DESCRIPTION_MESSAGE_KEY, fullName,
                classRoom, timeFrame.duration(), subjectName);

        return Description.of(description);

    }


    /**
     * Creates a description of class-room schedule based on lesson plan
     */
    private Description createClassRoomScheduleDescription(FullName fullName, ClassRoomName classRoom, Name subjectName, TimeFrame timeFrame) {
        String description = res.of(CLASS_ROOM_SCHEDULE_DESCRIPTION_MESSAGE_KEY, fullName,
                classRoom, timeFrame.duration(), subjectName);

        return Description.of(description);
    }

}
