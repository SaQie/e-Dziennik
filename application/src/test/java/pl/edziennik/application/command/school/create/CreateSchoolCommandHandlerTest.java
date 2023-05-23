package pl.edziennik.application.command.school.create;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.edziennik.application.BaseUnitTest;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.application.mock.repositories.SchoolLevelCommandMockRepo;
import pl.edziennik.common.valueobject.*;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.valueobject.id.SchoolLevelId;
import pl.edziennik.domain.school.School;
import pl.edziennik.infrastructure.spring.exception.BusinessException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

class CreateSchoolCommandHandlerTest extends BaseUnitTest {


    private final CreateSchoolCommandHandler commandHandler;

    public CreateSchoolCommandHandlerTest() {
        this.commandHandler = new CreateSchoolCommandHandler(schoolLevelCommandRepository,
                schoolCommandRepository,
                resourceCreator);
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

        Optional<School> school = schoolCommandRepository.findById(SchoolId.of(operationResult.identifier().id()));
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
        Assertions.assertThatThrownBy(() -> commandHandler.handle(command))
                .isInstanceOf(BusinessException.class)
                .hasMessage(assertNotFoundMessage());
    }
}