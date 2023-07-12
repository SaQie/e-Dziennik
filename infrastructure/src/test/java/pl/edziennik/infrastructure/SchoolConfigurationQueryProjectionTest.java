package pl.edziennik.infrastructure;

import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;
import pl.edziennik.BaseIntegrationTest;
import pl.edziennik.common.dto.school.config.SchoolConfigSummaryDto;
import pl.edziennik.common.enums.AverageType;
import pl.edziennik.common.valueobject.id.SchoolId;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DirtiesContext
public class SchoolConfigurationQueryProjectionTest extends BaseIntegrationTest {

    @Test
    public void shouldReturnSchoolConfigurationSummaryDto() {
        // given
        SchoolId schoolId = createSchool("Testowa", "123123123", "123123123");

        // when
        SchoolConfigSummaryDto dto = schoolConfigurationQueryRepository.getSchoolConfiguration(schoolId);

        // then
        assertNotNull(dto);
        assertEquals(dto.schoolId(), schoolId);
        assertEquals(dto.averageType(), AverageType.ARITHMETIC);
    }
}
