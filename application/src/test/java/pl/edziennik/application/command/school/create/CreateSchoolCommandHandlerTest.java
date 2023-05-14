package pl.edziennik.application.command.school.create;

import org.junit.jupiter.api.Test;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.application.common.dispatcher.exception.BusinessException;
import pl.edziennik.application.mock.ResourceCreatorMock;
import pl.edziennik.application.mock.repositories.SchoolCommandMockRepo;
import pl.edziennik.application.mock.repositories.SchoolLevelCommandMockRepo;
import pl.edziennik.common.valueobject.*;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.valueobject.id.SchoolLevelId;
import pl.edziennik.domain.school.School;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CreateSchoolCommandHandlerTest {


    private final SchoolCommandMockRepo schoolCommandMockRepo;
    private final SchoolLevelCommandMockRepo schoolLevelCommandMockRepo;
    private final ResourceCreatorMock resourceCreatorMock;
    private final CreateSchoolCommandHandler commandHandler;

    public CreateSchoolCommandHandlerTest() {
        this.schoolCommandMockRepo = new SchoolCommandMockRepo();
        this.schoolLevelCommandMockRepo = new SchoolLevelCommandMockRepo();
        this.resourceCreatorMock = new ResourceCreatorMock();
        this.commandHandler = new CreateSchoolCommandHandler(schoolLevelCommandMockRepo, schoolCommandMockRepo, resourceCreatorMock);
    }


    @Test
    public void shouldCreateNewSchool() {
        // given
        CreateSchoolCommand command = new CreateSchoolCommand(
                Name.of("School"),
                Address.of("Test"),
                PostalCode.of("Test"),
                City.of("xxx"),
                Nip.of("123123123"),
                Regon.of("123123123"),
                PhoneNumber.of("qweqew"),
                SchoolLevelCommandMockRepo.HIGH_SCHOOL_LEVEL_ID);


        // when
        OperationResult operationResult = commandHandler.handle(command);

        // then
        assertTrue(operationResult.isSuccess());

        Optional<School> school = schoolCommandMockRepo.findById(SchoolId.of(operationResult.identifier().id()));
        assertTrue(school.isPresent());
    }

    @Test
    public void shouldThrowExceptionIfSchoolLevelNotExists() {
        // given
        CreateSchoolCommand command = new CreateSchoolCommand(
                Name.of("School"),
                Address.of("Test"),
                PostalCode.of("Test"),
                City.of("xxx"),
                Nip.of("123123123"),
                Regon.of("123123123"),
                PhoneNumber.of("qweqew"),
                SchoolLevelId.create());

        // when
        // then
        assertThrows(BusinessException.class, () -> commandHandler.handle(command));
    }
}