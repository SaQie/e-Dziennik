package pl.edziennik.application.command.teacherschedule.create;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.CommandHandler;
import pl.edziennik.common.cache.CacheValueConstants;
import pl.edziennik.common.valueobject.vo.TimeFrame;
import pl.edziennik.domain.teacher.Teacher;
import pl.edziennik.domain.teacher.TeacherSchedule;
import pl.edziennik.infrastructure.repository.teacher.TeacherCommandRepository;
import pl.edziennik.infrastructure.repository.teacherschedule.TeacherScheduleCommandRepository;

@Component
@AllArgsConstructor
class CreateTeacherScheduleCommandHandler implements CommandHandler<CreateTeacherScheduleCommand> {

    private final TeacherCommandRepository teacherCommandRepository;
    private final TeacherScheduleCommandRepository teacherScheduleCommandRepository;

    @Override
    public void handle(CreateTeacherScheduleCommand command) {
        Teacher teacher = teacherCommandRepository.getByTeacherId(command.teacherId());

        TeacherSchedule teacherSchedule = TeacherSchedule.builder()
                .teacherScheduleId(command.teacherScheduleId())
                .teacher(teacher)
                .description(command.description())
                .timeFrame(TimeFrame.of(command.startDate(), command.endDate()))
                .build();

        teacherScheduleCommandRepository.save(teacherSchedule);
    }
}
