package pl.edziennik.application.command.classroomschedule.create;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.CommandHandler;
import pl.edziennik.common.valueobject.vo.TimeFrame;
import pl.edziennik.domain.classroom.ClassRoom;
import pl.edziennik.domain.classroom.ClassRoomSchedule;
import pl.edziennik.infrastructure.repository.classroom.ClassRoomCommandRepository;
import pl.edziennik.infrastructure.repository.classroomschedule.ClassRoomScheduleCommandRepository;

@Component
@AllArgsConstructor
class CreateClassRoomScheduleCommandHandler implements CommandHandler<CreateClassRoomScheduleCommand> {

    private final ClassRoomCommandRepository classRoomCommandRepository;
    private final ClassRoomScheduleCommandRepository classRoomScheduleCommandRepository;

    @Override
    public void handle(CreateClassRoomScheduleCommand command) {
        ClassRoom classRoom = classRoomCommandRepository.getById(command.classRoomId());
        TimeFrame timeFrame = TimeFrame.of(command.startDate(), command.endDate());

        ClassRoomSchedule classRoomSchedule = ClassRoomSchedule.builder()
                .timeFrame(timeFrame)
                .classRoom(classRoom)
                .description(command.description())
                .build();

        classRoomScheduleCommandRepository.save(classRoomSchedule);
    }
}
