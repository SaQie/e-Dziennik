package pl.edziennik.application.command.schoolclass.create;

import org.junit.jupiter.api.Test;
import pl.edziennik.application.BaseUnitTest;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.valueobject.id.TeacherId;
import pl.edziennik.common.valueobject.vo.Name;
import pl.edziennik.domain.schoolclass.SchoolClass;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class CreateSchoolClassCommandHandlerTest extends BaseUnitTest {

    private final CreateSchoolClassCommandHandler handler;

    public CreateSchoolClassCommandHandlerTest() {
        this.handler = new CreateSchoolClassCommandHandler(schoolCommandRepository,
                teacherCommandRepository,
                schoolClassCommandRepository,
                schoolClassConfigurationProperties);
    }

    @Test
    public void shouldCreateNewSchoolClass() {
        // given
        CreateSchoolClassCommand command = new CreateSchoolClassCommand(
                Name.of("Test"),
                TeacherId.create(),
                SchoolId.create()
        );

        // when
        handler.handle(command);

        // then
        SchoolClass schoolClass = schoolClassCommandRepository.getReferenceById(command.schoolClassId());
        assertNotNull(schoolClass);
    }


}
