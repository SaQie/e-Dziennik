package pl.edziennik.application.command.teacher.create;

import org.junit.jupiter.api.Test;
import pl.edziennik.application.BaseUnitTest;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.common.valueobject.*;
import pl.edziennik.common.valueobject.id.TeacherId;
import pl.edziennik.domain.school.School;
import pl.edziennik.domain.teacher.Teacher;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class CreateTeacherCommandHandlerTest extends BaseUnitTest {

    private final CreateTeacherCommandHandler handler;

    public CreateTeacherCommandHandlerTest() {
        this.handler = new CreateTeacherCommandHandler(teacherCommandRepository, schoolCommandRepository, passwordEncoder, roleCommandRepository, publisher);
    }

    @Test
    public void shouldCreateTeacher() {
        // given
        School school = createSchool("Test", "1231231", "123123", address);
        school = schoolCommandRepository.save(school);

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
                school.getSchoolId()
        );

        // when
        OperationResult operationResult = handler.handle(command);

        // then
        Teacher teacher = teacherCommandRepository.getReferenceById(TeacherId.of(operationResult.identifier().id()));
        assertNotNull(teacher);
    }
}
