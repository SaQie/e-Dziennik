package pl.edziennik.infrastructure;

import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;
import pl.edziennik.BaseIntegrationTest;
import pl.edziennik.common.enums.AverageType;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.view.school.config.SchoolConfigSummaryView;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DirtiesContext
public class SchoolConfigurationQueryProjectionTest extends BaseIntegrationTest {

    @Test
    public void shouldReturnSchoolConfigurationSummaryView() {
        // given
        SchoolId schoolId = createSchool("Testowa", "123123123", "123123123");

        // when
        SchoolConfigSummaryView view = schoolConfigurationQueryRepository.getSchoolConfigurationView(schoolId);

        // then
        assertNotNull(view);
        assertEquals(view.schoolId(), schoolId);
        assertEquals(view.averageType(), AverageType.ARITHMETIC);
    }
}
