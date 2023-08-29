package pl.edziennik.application.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;
import pl.edziennik.application.BaseIntegrationTest;
import pl.edziennik.application.command.schoolclass.create.CreateSchoolClassCommand;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.common.valueobject.id.SchoolClassId;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.valueobject.id.TeacherId;
import pl.edziennik.common.valueobject.vo.Name;
import pl.edziennik.domain.schoolclass.SchoolClass;
import pl.edziennik.infrastructure.spring.exception.BusinessException;
import pl.edziennik.infrastructure.validator.ValidationError;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DirtiesContext
public class SchoolClassIntegrationTest extends BaseIntegrationTest {

    @Test
    public void shouldCreateSchoolClass() {
        // given
        SchoolId schoolId = createSchool("Test", "1231231", "123123123");
        TeacherId teacherId = createTeacher("Test", "test@example.com", "1231233", schoolId);

        CreateSchoolClassCommand command = new CreateSchoolClassCommand(
                Name.of("1A"),
                teacherId,
                schoolId
        );

        // when
        OperationResult operationResult = dispatcher.dispatch(command);

        // then
        SchoolClass schoolClass = schoolClassCommandRepository.getBySchoolClassId(SchoolClassId.of(operationResult.identifier().id()));
        assertNotNull(schoolClass);
    }

    @Test
    public void shouldThrowExceptionIfSchoolClassAlreadyExistsInSchool() {
        // given
        SchoolId schoolId = createSchool("Testowa", "1231231", "1231231232");
        TeacherId teacherId = createTeacher("Nauczyciel", "test@example.com", "123123", schoolId);
        createSchoolClass(schoolId, teacherId, "1A");

        TeacherId secondTeacherId = createTeacher("Nauczyciel2", "Test@exampleee.com", "123123", schoolId);

        CreateSchoolClassCommand command = new CreateSchoolClassCommand(
                Name.of("1A"),
                secondTeacherId,
                schoolId
        );

        try {
            // when
            dispatcher.dispatch(command);
            Assertions.fail("Should throw exception when school class with given name exists in school");
        } catch (BusinessException e) {
            // then
            List<ValidationError> errors = e.getErrors();
            assertEquals(1, errors.size());
            assertThat(errors)
                    .extracting(ValidationError::field)
                    .containsExactlyInAnyOrder(CreateSchoolClassCommand.CLASS_NAME);
        }
    }


}
