package pl.edziennik.infrastructure;

import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;
import pl.edziennik.BaseIntegrationTest;
import pl.edziennik.common.dto.schoolclass.config.SchoolClassConfigSummaryDto;
import pl.edziennik.common.valueobject.id.SchoolClassId;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.valueobject.id.TeacherId;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DirtiesContext
public class SchoolClassConfigurationQueryProjectionTest extends BaseIntegrationTest {

    private final String expectedSchoolClassName = "1A";

    @Test
    public void shouldReturnSchoolClassConfigSummaryDto() {
        // given
        SchoolId schoolId = createSchool("testt", "123123123123", "123123123");
        TeacherId teacherId = createTeacher("Test", "test@example.com", "12312312312", schoolId);

        SchoolClassId schoolClassId = createSchoolClass(schoolId, teacherId, expectedSchoolClassName);

        // when
        SchoolClassConfigSummaryDto configurationSummaryDto = schoolClassConfigurationQueryRepository.getSchoolClassConfigurationSummaryDto(schoolClassId);

        // then
        assertEquals(configurationSummaryDto.schoolClassId(), schoolClassId);
        assertEquals(configurationSummaryDto.schoolClassName().value(), expectedSchoolClassName);
        assertNotNull(configurationSummaryDto.autoAssignSubjects());
        assertNotNull(configurationSummaryDto.maxStudentsSize());
    }

}
