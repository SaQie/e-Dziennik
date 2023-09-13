package pl.edziennik.application.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;
import pl.edziennik.application.BaseIntegrationTest;
import pl.edziennik.application.command.classroom.create.CreateClassRoomCommand;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.valueobject.vo.ClassRoomName;
import pl.edziennik.domain.classroom.ClassRoom;
import pl.edziennik.infrastructure.spring.exception.BusinessException;
import pl.edziennik.infrastructure.validator.ValidationError;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DirtiesContext
public class ClassRoomIntegrationTest extends BaseIntegrationTest {

    @Test
    public void shouldCreateNewClassRoom() {
        // given
        SchoolId schoolId = createSchool("TEST", "123123123", "123123123");

        CreateClassRoomCommand command = new CreateClassRoomCommand(schoolId, ClassRoomName.of("TEST"));

        // when
        dispatcher.run(command);

        // then
        ClassRoom classRoom = classRoomCommandRepository.getById(command.classRoomId());
        assertNotNull(classRoom);
    }

    @Test
    public void shouldThrowExceptionIfGivenClassRoomNameAlreadyExistsInSchool() {
        // given
        SchoolId schoolId = createSchool("TEST", "123123123", "123123123");
        createClassRoom(schoolId, "TEST");

        CreateClassRoomCommand command = new CreateClassRoomCommand(schoolId, ClassRoomName.of("TEST"));

        try {
            // when
            dispatcher.run(command);
            Assertions.fail("Should throw exception if class room with given name already exists");
            // then
        } catch (BusinessException e) {
            List<ValidationError> errors = e.getErrors();
            assertEquals(1, errors.size());
            assertThat(errors)
                    .extracting(ValidationError::field)
                    .containsExactlyInAnyOrder(CreateClassRoomCommand.CLASS_ROOM_NAME);
        }
    }

    @Test
    public void shouldCreateNewClassRoomIfGivenNameExistsButInAnotherSchool() {
        // given
        SchoolId schoolId = createSchool("TEST", "123123123", "123123123");
        SchoolId schoolIdSecond = createSchool("TEST2", "12342424", "12312535");
        createClassRoom(schoolId, "TEST");

        CreateClassRoomCommand command = new CreateClassRoomCommand(schoolIdSecond, ClassRoomName.of("TEST"));

        // when
        dispatcher.run(command);

        // then
        ClassRoom classRoom = classRoomCommandRepository.getById(command.classRoomId());
        assertNotNull(classRoom);
    }
}
