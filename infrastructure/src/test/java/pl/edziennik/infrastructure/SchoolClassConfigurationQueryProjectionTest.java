package pl.edziennik.infrastructure;

import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;
import pl.edziennik.BaseIntegrationTest;
import pl.edziennik.common.valueobject.id.SchoolClassId;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.valueobject.id.TeacherId;
import pl.edziennik.common.view.schoolclass.config.SchoolClassConfigSummaryView;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DirtiesContext
public class SchoolClassConfigurationQueryProjectionTest extends BaseIntegrationTest {

    private final String expectedSchoolClassName = "1A";

    @Test
    public void shouldReturnSchoolClassConfigSummaryView() {
        // given
        SchoolId schoolId = createSchool("testt", "123123123123", "123123123");
        TeacherId teacherId = createTeacher("Test", "test@example.com", "12312312312", schoolId);

        SchoolClassId schoolClassId = createSchoolClass(schoolId, teacherId, expectedSchoolClassName);

        // when
        SchoolClassConfigSummaryView configurationSummaryView = schoolClassConfigurationQueryRepository.getSchoolClassConfigurationSummaryView(schoolClassId);

        // then
        assertEquals(configurationSummaryView.schoolClassId(), schoolClassId);
        assertEquals(configurationSummaryView.schoolClassName().value(), expectedSchoolClassName);
        assertNotNull(configurationSummaryView.autoAssignSubjects());
        assertNotNull(configurationSummaryView.maxStudentsSize());
    }

}
