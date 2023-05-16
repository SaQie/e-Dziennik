package pl.edziennik.application.command.teacher.create;

import org.junit.jupiter.api.Test;
import pl.edziennik.application.BaseUnitTest;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.common.valueobject.*;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.valueobject.id.TeacherId;
import pl.edziennik.domain.teacher.Teacher;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class CreateTeacherCommandHandlerTest extends BaseUnitTest {

    private final CreateTeacherCommandHandler handler;

    public CreateTeacherCommandHandlerTest() {
        this.handler = new CreateTeacherCommandHandler(teacherCommandRepository, schoolCommandRepository, passwordEncoder, roleCommandRepository);
    }

    @Test
    public void shouldCreateTeacher(){
        // given
        CreateTeacherCommand command = new CreateTeacherCommand(
                Password.of("password"),
                Username.of("Test"),
                FirstName.of("Test"),
                LastName.of("Test"),
                Address.of("address"),
                PostalCode.of("12-12"),
                City.of("Test"),
                Pesel.of("123123123"),
                Email.of("test@example.com"),
                PhoneNumber.of("123000000"),
                SchoolId.create()
        );

        // when
        OperationResult operationResult = handler.handle(command);

        // then
        Teacher teacher = teacherCommandRepository.getReferenceById(TeacherId.of(operationResult.identifier().id()));
        assertNotNull(teacher);
    }
}
