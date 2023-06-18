package pl.edziennik.infrastructure;

import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;
import pl.edziennik.BaseIntegrationTest;
import pl.edziennik.common.dto.director.DetailedDirectorDto;
import pl.edziennik.common.valueobject.id.DirectorId;
import pl.edziennik.common.valueobject.id.SchoolId;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DirtiesContext
public class DirectorQueryProjectionTest extends BaseIntegrationTest {


    @Test
    public void shouldReturnDetialedDirectorDto() {
        // given
        final String expectedSchoolName = "Testowa";
        final String expectedDirectorName = "Test Test";
        final String expectedDirectorUsername = "Ktos";
        final String expectedDirectorEmail = "Test@example.com";

        SchoolId schoolId = createSchool(expectedSchoolName, "123123", "123123");
        DirectorId directorId = createDirector(expectedDirectorUsername, expectedDirectorEmail, "123123123", schoolId);

        // when
        DetailedDirectorDto dto = directorQueryRepository.getDirector(directorId);

        // then
        assertNotNull(dto);
        assertEquals(dto.directorId(),directorId);
        assertEquals(dto.schoolId(),schoolId);
        assertEquals(dto.fullName().value(),expectedDirectorName);
        assertEquals(dto.email().value(), expectedDirectorEmail);
        assertEquals(dto.username().value(),expectedDirectorUsername);
        assertEquals(dto.schoolName().value(),expectedSchoolName);
    }

}
