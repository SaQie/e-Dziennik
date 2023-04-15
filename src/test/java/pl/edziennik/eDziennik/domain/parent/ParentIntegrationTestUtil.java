package pl.edziennik.eDziennik.domain.parent;

import org.apache.commons.lang3.RandomStringUtils;
import pl.edziennik.eDziennik.domain.parent.domain.dto.ParentRequestApiDto;
import pl.edziennik.eDziennik.domain.student.domain.wrapper.StudentId;

/**
 * Util class for parent integration tests {@link ParentIntegrationTest}
 */
public class ParentIntegrationTestUtil {

    public ParentRequestApiDto prepareParentRequestApiDto() {
        return new ParentRequestApiDto(
                "test",
                RandomStringUtils.random(8),
                "Kazik",
                "Kazikowy",
                "Washington",
                "11-111",
                "IdontKnow",
                RandomStringUtils.randomNumeric(9),
                RandomStringUtils.random(8),
                "123123",
                StudentId.wrap(1L)
        );
    }

    public ParentRequestApiDto prepareParentRequestApiDto(StudentId studentId) {
        return new ParentRequestApiDto(
                "test",
                RandomStringUtils.random(8),
                "Kazik",
                "Kazikowy",
                "Washington",
                "11-111",
                "IdontKnow",
                RandomStringUtils.randomNumeric(9),
                RandomStringUtils.random(8),
                "123123",
                studentId
        );
    }

    public ParentRequestApiDto prepareParentRequestApiDto(String pesel, StudentId studentId) {
        return new ParentRequestApiDto(
                "test",
                RandomStringUtils.random(8),
                "Kazik",
                "Kazikowy",
                "Washington",
                "11-111",
                "IdontKnow",
                pesel,
                RandomStringUtils.random(8),
                "123123",
                studentId
        );
    }

    public ParentRequestApiDto prepareParentRequestApiDto(StudentId studentId, String username, String email) {
        return new ParentRequestApiDto(
                "test",
                username,
                "Kazik",
                "Kazikowy",
                "Washington",
                "11-111",
                "IdontKnow",
                "81818181818",
                email,
                "123123",
                studentId
        );
    }


    public ParentRequestApiDto prepareParentRequestApiDto(String firstName, String lastName,StudentId studentId, String username, String email) {
        return new ParentRequestApiDto(
                "test",
                username,
                firstName,
                lastName,
                "Washington",
                "11-111",
                "IdontKnow",
                "81818181818",
                email,
                "123123",
                studentId
        );
    }
}
