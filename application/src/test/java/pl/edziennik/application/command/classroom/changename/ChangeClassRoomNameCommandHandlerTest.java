package pl.edziennik.application.command.classroom.changename;

import org.junit.jupiter.api.Test;
import pl.edziennik.application.BaseUnitTest;
import pl.edziennik.common.valueobject.vo.ClassRoomName;
import pl.edziennik.domain.classroom.ClassRoom;
import pl.edziennik.domain.school.School;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChangeClassRoomNameCommandHandlerTest extends BaseUnitTest {

    private final ChangeClassRoomNameCommandHandler handler;

    public ChangeClassRoomNameCommandHandlerTest() {
        this.handler = new ChangeClassRoomNameCommandHandler(classRoomCommandRepository);
    }


    @Test
    public void shouldChangeClassRoomName() {
        // given
        School school = createSchool("TEST", "123123", "123123", address);
        ClassRoom classRoom = createClassRoom("122A", school);

        ClassRoomName newName = ClassRoomName.of("TEST");
        ChangeClassRoomNameCommand command = new ChangeClassRoomNameCommand(classRoom.classRoomId(), newName);

        // when
        handler.handle(command);

        // then
        ClassRoom classRoomAfterChange = classRoomCommandRepository.getById(classRoom.classRoomId());
        assertEquals(newName, classRoomAfterChange.classRoomName());
    }
}
