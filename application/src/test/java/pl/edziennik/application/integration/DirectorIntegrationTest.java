package pl.edziennik.application.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;
import pl.edziennik.application.BaseIntegrationTest;
import pl.edziennik.application.command.director.create.CreateDirectorCommand;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.common.valueobject.*;
import pl.edziennik.common.valueobject.id.DirectorId;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.domain.director.Director;
import pl.edziennik.domain.school.School;
import pl.edziennik.infrastructure.spring.exception.BusinessException;
import pl.edziennik.infrastructure.validator.ValidationError;
import pl.edziennik.infrastructure.validator.errorcode.ErrorCode;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DirtiesContext
public class DirectorIntegrationTest extends BaseIntegrationTest {

    @Test
    public void shouldCreateDirector() {
        // given
        SchoolId schoolId = createSchool("Test", "12312313", "123123132");
        CreateDirectorCommand command = new CreateDirectorCommand(
                Password.of("password"),
                Username.of("Test"),
                FirstName.of("Test"),
                LastName.of("Test"),
                Address.of("address"),
                PostalCode.of("12-12"),
                City.of("Test"),
                Pesel.of("123123123"),
                Email.of("Test@example.com"),
                PhoneNumber.of("123000000"),
                schoolId
        );

        // when
        OperationResult operationResult = dispatcher.dispatch(command);

        // then
        Director director = directorCommandRepository.getByDirectorId(DirectorId.of(operationResult.identifier().id()));
        Assertions.assertNotNull(director);
        School school = schoolCommandRepository.getBySchoolId(schoolId);
        assertEquals(school.getDirector().getDirectorId(), director.getDirectorId());
    }

    @Test
    public void shouldThrowExceptionWhileCreatingNewDirectorAndPassedSchoolAlreadyHasAssignedDirector() {
        // given
        SchoolId schoolId = createSchool("Test", "12312313", "123123132");
        createDirector("Testowy", "aaa@o2.pl", "1112333444", schoolId);
        CreateDirectorCommand command = new CreateDirectorCommand(
                Password.of("password"),
                Username.of("Test1"),
                FirstName.of("Test"),
                LastName.of("Test"),
                Address.of("address"),
                PostalCode.of("12-12"),
                City.of("Test"),
                Pesel.of("123123124"),
                Email.of("Test1@example.com"),
                PhoneNumber.of("123000000"),
                schoolId
        );

        try {
            // when
            dispatcher.dispatch(command);
            Assertions.fail("Should throw exception when creating new director and passed school already has assigned director");
        } catch (BusinessException e) {
            // then
            List<ValidationError> errors = e.getErrors();
            assertEquals(1, errors.size());
            assertEquals(errors.get(0).field(), CreateDirectorCommand.SCHOOL_ID);
            assertEquals(errors.get(0).errorCode(), ErrorCode.SCHOOL_ALREADY_HAS_ASSIGNED_DIRECTOR.errorCode());
        }
    }


}
