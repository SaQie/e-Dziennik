package pl.edziennik.application.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import pl.edziennik.application.BaseIntegrationTest;
import pl.edziennik.application.command.school.changeconfig.ChangeSchoolConfigurationValuesCommand;
import pl.edziennik.application.command.school.create.CreateSchoolCommand;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.common.enums.AverageType;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.valueobject.id.SchoolLevelId;
import pl.edziennik.common.valueobject.vo.*;
import pl.edziennik.domain.school.School;
import pl.edziennik.infrastructure.spring.exception.BusinessException;
import pl.edziennik.infrastructure.validator.ValidationError;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DirtiesContext
public class SchoolIntegrationTest extends BaseIntegrationTest {

    @Test
    public void shouldCreateSchool() {
        // given
        CreateSchoolCommand command = new CreateSchoolCommand(
                Name.of("Test"),
                Address.of("Test"),
                PostalCode.of("Test"),
                City.of("xxx"),
                Nip.of("9999999"),
                Regon.of("9999999"),
                PhoneNumber.of("qweqew"),
                SchoolLevelId.PredefinedRow.PRIMARY_SCHOOL);

        // when
        OperationResult operationResult = dispatcher.dispatch(command);

        // then
        School school = schoolCommandRepository.getBySchoolId(SchoolId.of(operationResult.identifier().id()));
        Assertions.assertNotNull(school);
    }

    @Test
    public void shouldThrowExceptionIfSchoolWithRegonOrNameOrNipAlreadyExists() {
        // given
        createSchool("Test", "9999999", "9999999");

        CreateSchoolCommand command = new CreateSchoolCommand(
                Name.of("Test"),
                Address.of("Test"),
                PostalCode.of("Test"),
                City.of("xxx"),
                Nip.of("9999999"),
                Regon.of("9999999"),
                PhoneNumber.of("qweqew"),
                SchoolLevelId.PredefinedRow.PRIMARY_SCHOOL);

        try {
            // when
            dispatcher.dispatch(command);
            Assertions.fail("Should throw business exception when school already exists with given name, pesel or nip");
        } catch (BusinessException e) {
            // then
            List<ValidationError> errors = e.getErrors();
            assertEquals(3, errors.size());
            assertThat(errors)
                    .extracting(ValidationError::field)
                    .containsExactlyInAnyOrder(CreateSchoolCommand.NAME, CreateSchoolCommand.REGON, CreateSchoolCommand.NIP);
        }
    }

    @Test
    @Transactional
    public void shouldChangeSchoolConfiguration() {
        // given
        SchoolId schoolId = createSchool("Test", "9999999", "9999999");

        ChangeSchoolConfigurationValuesCommand command = new ChangeSchoolConfigurationValuesCommand(schoolId, AverageType.WEIGHTED);

        // when
        dispatcher.dispatch(command);

        // then
        School school = schoolCommandRepository.getBySchoolId(schoolId);
        assertEquals(school.schoolConfiguration().averageType(), AverageType.WEIGHTED);
    }

}
