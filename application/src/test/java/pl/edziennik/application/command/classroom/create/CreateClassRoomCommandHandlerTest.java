package pl.edziennik.application.command.classroom.create;

import org.junit.jupiter.api.Test;
import pl.edziennik.application.BaseUnitTest;
import pl.edziennik.common.valueobject.vo.ClassRoomName;
import pl.edziennik.domain.classroom.ClassRoom;
import pl.edziennik.domain.school.School;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class CreateClassRoomCommandHandlerTest extends BaseUnitTest {

    private final CreateClassRoomCommandHandler handler;

    public CreateClassRoomCommandHandlerTest() {
        this.handler = new CreateClassRoomCommandHandler(classRoomCommandRepository, schoolCommandRepository);
    }

    @Test
    public void shouldCreateNewClassRoom() {
        // given
        School school = createSchool("TEST", "123123123", "123123", address);

        CreateClassRoomCommand command = new CreateClassRoomCommand(school.schoolId(), ClassRoomName.of("TEST"));

        // when
        handler.handle(command);

        // then
        ClassRoom classRoom = classRoomCommandRepository.getById(command.classRoomId());
        assertNotNull(classRoom);
    }
}
