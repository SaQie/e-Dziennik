package pl.edziennik.eDziennik.domain.parent;

import liquibase.repackaged.org.apache.commons.lang3.RandomStringUtils;
import pl.edziennik.eDziennik.domain.parent.domain.dto.ParentRequestApiDto;

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
                1L
        );
    }

    public ParentRequestApiDto prepareParentRequestApiDto(Long idStudent) {
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
                idStudent
        );
    }

    public ParentRequestApiDto prepareParentRequestApiDto(String pesel, Long idStudent) {
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
                idStudent
        );
    }

    public ParentRequestApiDto prepareParentRequestApiDto(Long idStudent, String username, String email) {
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
                idStudent
        );
    }


    public ParentRequestApiDto prepareParentRequestApiDto(String firstName, String lastName,Long idStudent, String username, String email) {
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
                idStudent
        );
    }
}
