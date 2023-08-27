package pl.edziennik.application.command.classroom.changename;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.application.common.dispatcher.command.ICommandHandler;
import pl.edziennik.domain.classroom.ClassRoom;
import pl.edziennik.infrastructure.repository.classroom.ClassRoomCommandRepository;

@Component
@AllArgsConstructor
class ChangeClassRoomNameCommandHandler implements ICommandHandler<ChangeClassRoomNameCommand, OperationResult> {

    private final ClassRoomCommandRepository classRoomCommandRepository;

    @Override
    public OperationResult handle(ChangeClassRoomNameCommand command) {
        ClassRoom classRoom = classRoomCommandRepository.getById(command.classRoomId());
        classRoom.changeName(command.classRoomName());

        classRoomCommandRepository.save(classRoom);

        return OperationResult.success(classRoom.classRoomId());
    }
}
