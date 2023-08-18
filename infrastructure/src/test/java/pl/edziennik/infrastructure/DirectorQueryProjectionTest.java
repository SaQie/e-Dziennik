package pl.edziennik.infrastructure;

import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;
import pl.edziennik.BaseIntegrationTest;
import pl.edziennik.common.valueobject.id.DirectorId;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.view.director.DetailedDirectorView;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DirtiesContext
public class DirectorQueryProjectionTest extends BaseIntegrationTest {


    @Test
    public void shouldReturnDetialedDirectorView() {
        // given
        final String expectedSchoolName = "Testowa";
        final String expectedDirectorName = "Test Test";
        final String expectedDirectorUsername = "Ktos";
        final String expectedDirectorEmail = "Test@example.com";

        SchoolId schoolId = createSchool(expectedSchoolName, "123123", "123123");
        DirectorId directorId = createDirector(expectedDirectorUsername, expectedDirectorEmail, "123123123", schoolId);

        // when
        DetailedDirectorView view = directorQueryRepository.getDirectorView(directorId);

        // then
        assertNotNull(view);
        assertEquals(view.directorId(),directorId);
        assertEquals(view.schoolId(),schoolId);
        assertEquals(view.fullName().value(),expectedDirectorName);
        assertEquals(view.email().value(), expectedDirectorEmail);
        assertEquals(view.username().value(),expectedDirectorUsername);
        assertEquals(view.schoolName().value(),expectedSchoolName);
    }

}
