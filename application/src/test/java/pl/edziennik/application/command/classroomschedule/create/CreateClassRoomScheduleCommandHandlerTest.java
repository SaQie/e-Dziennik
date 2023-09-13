package pl.edziennik.application.command.classroomschedule.create;

import org.junit.jupiter.api.Test;
import pl.edziennik.application.BaseUnitTest;
import pl.edziennik.common.valueobject.vo.Description;
import pl.edziennik.domain.classroom.ClassRoom;
import pl.edziennik.domain.classroom.ClassRoomSchedule;
import pl.edziennik.domain.school.School;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class CreateClassRoomScheduleCommandHandlerTest extends BaseUnitTest {

    // 10:00 -> 10:30
    private static final LocalDateTime DATE_2022_01_01_10_00 = LocalDateTime.of(2022, 1, 1, 10, 0);
    private static final LocalDateTime DATE_2022_01_01_10_30 = LocalDateTime.of(2022, 1, 1, 10, 30);

    private final CreateClassRoomScheduleCommandHandler handler;

    public CreateClassRoomScheduleCommandHandlerTest() {
        this.handler = new CreateClassRoomScheduleCommandHandler(classRoomCommandRepository, classRoomScheduleCommandRepository);
    }


    @Test
    public void shouldCreateNewClassRoomSchedule() {
        // given
        School school = createSchool("TEST", "123123123", "231232", address);
        school = schoolCommandRepository.save(school);

        ClassRoom classRoom = createClassRoom("122A", school);

        CreateClassRoomScheduleCommand command = new CreateClassRoomScheduleCommand(classRoom.classRoomId(),
                Description.of("TEST"), DATE_2022_01_01_10_00, DATE_2022_01_01_10_30);

        // when
        handler.handle(command);

        // then
        ClassRoomSchedule schedule = classRoomScheduleCommandRepository.getReferenceById(command.classRoomScheduleId());
        assertNotNull(schedule);
    }
}
