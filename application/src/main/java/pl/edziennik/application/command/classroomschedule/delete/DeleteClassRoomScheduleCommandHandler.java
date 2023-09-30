package pl.edziennik.application.command.classroomschedule.delete;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.CommandHandler;
import pl.edziennik.infrastructure.repository.classroomschedule.ClassRoomScheduleCommandRepository;

@Component
@AllArgsConstructor
class DeleteClassRoomScheduleCommandHandler implements CommandHandler<DeleteClassRoomScheduleCommand> {

    private final ClassRoomScheduleCommandRepository classRoomScheduleCommandRepository;

    @Override
    public void handle(DeleteClassRoomScheduleCommand command) {
        classRoomScheduleCommandRepository.deleteById(command.classRoomScheduleId());
    }
}
