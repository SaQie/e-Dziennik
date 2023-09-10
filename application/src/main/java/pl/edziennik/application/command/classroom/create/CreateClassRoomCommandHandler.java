package pl.edziennik.application.command.classroom.create;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.CommandHandler;
import pl.edziennik.domain.classroom.ClassRoom;
import pl.edziennik.domain.school.School;
import pl.edziennik.infrastructure.repository.classroom.ClassRoomCommandRepository;
import pl.edziennik.infrastructure.repository.school.SchoolCommandRepository;

@Component
@AllArgsConstructor
class CreateClassRoomCommandHandler implements CommandHandler<CreateClassRoomCommand> {

    private final ClassRoomCommandRepository classRoomCommandRepository;
    private final SchoolCommandRepository schoolCommandRepository;

    @Override
    public void handle(CreateClassRoomCommand command) {
        School school = schoolCommandRepository.getReferenceById(command.schoolId());

        ClassRoom classRoom = ClassRoom.builder()
                .classRoomName(command.classRoomName())
                .school(school)
                .build();

        classRoomCommandRepository.save(classRoom);
    }
}
