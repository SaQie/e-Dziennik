package pl.edziennik.application.command.teacherschedule.delete;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.CommandHandler;
import pl.edziennik.infrastructure.repository.teacherschedule.TeacherScheduleCommandRepository;

@Component
@AllArgsConstructor
class DeleteTeacherScheduleCommandHandler implements CommandHandler<DeleteTeacherScheduleCommand> {

    private final TeacherScheduleCommandRepository teacherScheduleCommandRepository;

    @Override
    public void handle(DeleteTeacherScheduleCommand command) {
        teacherScheduleCommandRepository.deleteById(command.teacherScheduleId());
    }
}
