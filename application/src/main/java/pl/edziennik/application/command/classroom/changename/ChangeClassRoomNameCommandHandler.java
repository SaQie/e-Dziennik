package pl.edziennik.application.command.classroom.changename;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.CommandHandler;
import pl.edziennik.domain.classroom.ClassRoom;
import pl.edziennik.infrastructure.repository.classroom.ClassRoomCommandRepository;

@Component
@AllArgsConstructor
class ChangeClassRoomNameCommandHandler implements CommandHandler<ChangeClassRoomNameCommand> {

    private final ClassRoomCommandRepository classRoomCommandRepository;

    @Override
    public void handle(ChangeClassRoomNameCommand command) {
        ClassRoom classRoom = classRoomCommandRepository.getById(command.classRoomId());
        classRoom.changeName(command.classRoomName());

        classRoomCommandRepository.save(classRoom);
    }
}
