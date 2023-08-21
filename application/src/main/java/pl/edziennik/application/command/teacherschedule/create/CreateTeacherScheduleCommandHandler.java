package pl.edziennik.application.command.teacherschedule.create;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.application.common.dispatcher.command.ICommandHandler;
import pl.edziennik.common.valueobject.id.TeacherScheduleId;
import pl.edziennik.common.valueobject.vo.TimeFrame;
import pl.edziennik.domain.teacher.Teacher;
import pl.edziennik.domain.teacher.TeacherSchedule;
import pl.edziennik.infrastructure.repository.teacher.TeacherCommandRepository;
import pl.edziennik.infrastructure.repository.teacherschedule.TeacherScheduleCommandRepository;

@Component
@AllArgsConstructor
class CreateTeacherScheduleCommandHandler implements ICommandHandler<CreateTeacherScheduleCommand, OperationResult> {

    private final TeacherCommandRepository teacherCommandRepository;
    private final TeacherScheduleCommandRepository teacherScheduleCommandRepository;

    @Override
    public OperationResult handle(CreateTeacherScheduleCommand command) {
        Teacher teacher = teacherCommandRepository.getByTeacherId(command.teacherId());

        TeacherSchedule teacherSchedule = TeacherSchedule.builder()
                .teacher(teacher)
                .description(command.description())
                .timeFrame(TimeFrame.of(command.startDate(), command.endDate()))
                .build();

        TeacherScheduleId teacherScheduleId = teacherScheduleCommandRepository.save(teacherSchedule).teacherScheduleId();

        return OperationResult.success(teacherScheduleId);
    }
}
