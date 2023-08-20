package pl.edziennik.application.command.classroom.create;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.application.common.dispatcher.command.ICommandHandler;
import pl.edziennik.common.valueobject.id.ClassRoomId;
import pl.edziennik.domain.classroom.ClassRoom;
import pl.edziennik.domain.school.School;
import pl.edziennik.infrastructure.repository.classroom.ClassRoomCommandRepository;
import pl.edziennik.infrastructure.repository.school.SchoolCommandRepository;

@Component
@AllArgsConstructor
class CreateClassRoomCommandHandler implements ICommandHandler<CreateClassRoomCommand, OperationResult> {

    private final ClassRoomCommandRepository classRoomCommandRepository;
    private final SchoolCommandRepository schoolCommandRepository;

    @Override
    public OperationResult handle(CreateClassRoomCommand command) {
        School school = schoolCommandRepository.getReferenceById(command.schoolId());

        ClassRoom classRoom = ClassRoom.builder()
                .classRoomName(command.classRoomName())
                .school(school)
                .build();

        ClassRoomId classRoomId = classRoomCommandRepository.save(classRoom).classRoomId();

        return OperationResult.success(classRoomId);
    }
}
