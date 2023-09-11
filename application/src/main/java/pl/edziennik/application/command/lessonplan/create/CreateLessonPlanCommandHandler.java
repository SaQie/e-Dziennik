package pl.edziennik.application.command.lessonplan.create;

import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.application.common.dispatcher.CommandHandler;
import pl.edziennik.application.events.event.LessonPlanCreatedEvent;
import pl.edziennik.common.valueobject.id.LessonPlanId;
import pl.edziennik.common.valueobject.vo.LessonOrder;
import pl.edziennik.common.valueobject.vo.TimeFrame;
import pl.edziennik.domain.classroom.ClassRoom;
import pl.edziennik.domain.lessonplan.LessonPlan;
import pl.edziennik.domain.schoolclass.SchoolClass;
import pl.edziennik.domain.subject.Subject;
import pl.edziennik.domain.teacher.Teacher;
import pl.edziennik.infrastructure.repository.classroom.ClassRoomCommandRepository;
import pl.edziennik.infrastructure.repository.lessonplan.LessonPlanCommandRepository;
import pl.edziennik.infrastructure.repository.schoolclass.SchoolClassCommandRepository;
import pl.edziennik.infrastructure.repository.subject.SubjectCommandRepository;
import pl.edziennik.infrastructure.repository.teacher.TeacherCommandRepository;
import pl.edziennik.infrastructure.spring.ResourceCreator;
import pl.edziennik.infrastructure.spring.exception.BusinessException;

import java.time.LocalDateTime;

@Component
@AllArgsConstructor
class CreateLessonPlanCommandHandler implements CommandHandler<CreateLessonPlanCommand> {

    private final LessonPlanCommandRepository lessonPlanCommandRepository;
    private final ClassRoomCommandRepository classRoomCommandRepository;
    private final SubjectCommandRepository subjectCommandRepository;
    private final SchoolClassCommandRepository schoolClassCommandRepository;
    private final TeacherCommandRepository teacherCommandRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final ResourceCreator res;

    @Override
    @Transactional
    public void handle(CreateLessonPlanCommand command) {
        ClassRoom classRoom = classRoomCommandRepository.getById(command.classRoomId());
        Subject subject = subjectCommandRepository.getBySubjectId(command.subjectId());
        SchoolClass schoolClass = schoolClassCommandRepository.getBySchoolClassId(command.schoolClassId());
        Teacher teacher = subject.teacher();

        LessonOrder lessonOrder = getLessonOrder(command);
        boolean isTeacherProvided = command.teacherId() != null;

        if (isTeacherProvided) {
            // When someone provided the teacher (substitute)
            teacher = teacherCommandRepository.findById(command.teacherId())
                    .orElseThrow(() ->
                            new BusinessException(res.notFoundError(CreateLessonPlanCommand.TEACHER_ID, command.teacherId())));
        }

        LessonPlan lessonPlan = LessonPlan.builder()
                .lessonPlanId(command.lessonPlanId())
                .lessonOrder(lessonOrder)
                .classRoom(classRoom)
                .subject(subject)
                .schoolClass(schoolClass)
                .timeFrame(TimeFrame.of(command.startDate(), command.endDate()))
                .isSubstitute(isTeacherProvided)
                .teacher(teacher)
                .build();

        LessonPlanId lessonPlanId = lessonPlanCommandRepository.save(lessonPlan).lessonPlanId();

        LessonPlanCreatedEvent event = new LessonPlanCreatedEvent(lessonPlanId);
        eventPublisher.publishEvent(event);
    }

    /**
     * Returns correct lesson order based on last lesson order in day provided by command
     */
    private LessonOrder getLessonOrder(CreateLessonPlanCommand command) {
        LocalDateTime startDate = command.startDate().toLocalDate().atStartOfDay();
        LocalDateTime endDate = command.endDate().toLocalDate().plusDays(1).atStartOfDay();

        LessonOrder lastLessonOrderInDay = lessonPlanCommandRepository.getLastLessonOrderInDay(startDate, endDate)
                .orElse(LessonOrder.of(0));

        return lastLessonOrderInDay.increase();
    }
}
