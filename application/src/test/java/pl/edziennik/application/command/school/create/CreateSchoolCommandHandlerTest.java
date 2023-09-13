package pl.edziennik.application.command.school.create;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.edziennik.application.BaseUnitTest;
import pl.edziennik.application.mock.repositories.SchoolLevelCommandMockRepo;
import pl.edziennik.common.valueobject.id.SchoolLevelId;
import pl.edziennik.common.valueobject.vo.*;
import pl.edziennik.domain.school.School;
import pl.edziennik.infrastructure.spring.exception.BusinessException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

class CreateSchoolCommandHandlerTest extends BaseUnitTest {


    private final CreateSchoolCommandHandler commandHandler;

    public CreateSchoolCommandHandlerTest() {
        this.commandHandler = new CreateSchoolCommandHandler(schoolLevelCommandRepository,
                schoolCommandRepository,
                schoolConfigurationProperties,
                resourceCreator
        );
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
        commandHandler.handle(command);

        // then
        Optional<School> school = schoolCommandRepository.findById(command.schoolId());
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