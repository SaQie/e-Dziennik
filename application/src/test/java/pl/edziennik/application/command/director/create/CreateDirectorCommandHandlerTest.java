package pl.edziennik.application.command.director.create;

import org.junit.jupiter.api.Test;
import pl.edziennik.application.BaseUnitTest;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.common.valueobject.*;
import pl.edziennik.common.valueobject.id.DirectorId;
import pl.edziennik.domain.director.Director;
import pl.edziennik.domain.school.School;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CreateDirectorCommandHandlerTest extends BaseUnitTest {

    private final CreateDirectorCommandHandler handler;

    public CreateDirectorCommandHandlerTest() {
        this.handler = new CreateDirectorCommandHandler(directorCommandRepository,
                schoolCommandRepository,
                roleCommandRepository,
                passwordEncoder);
    }

    @Test
    public void shouldCreateNewDirector() {
        // given
        School school = createSchool("Test", "123123", "123123123", address);
        school = schoolCommandRepository.save(school);

        CreateDirectorCommand command = new CreateDirectorCommand(
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
        Director director = directorCommandRepository.getByDirectorId(DirectorId.of(operationResult.identifier().id()));
        assertNotNull(director);
    }

}
