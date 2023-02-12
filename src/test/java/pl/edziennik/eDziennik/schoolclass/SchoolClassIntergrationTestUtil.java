package pl.edziennik.eDziennik.schoolclass;

import pl.edziennik.eDziennik.domain.schoolclass.dto.SchoolClassRequestApiDto;

/**
 * Util class for student integration tests {@link SchoolClassIntegrationTest}
 */
public class SchoolClassIntergrationTestUtil {


    public SchoolClassRequestApiDto prepareSchoolClassRequest() {
        return new SchoolClassRequestApiDto(
                "6B",
                null,
                100L
        );
    }

    public SchoolClassRequestApiDto prepareSchoolClassRequest(final String className, final Long idTeacher) {
        return new SchoolClassRequestApiDto(
                className,
                idTeacher,
                100L
        );
    }

    public SchoolClassRequestApiDto prepareSchoolClassRequest(final Long idTeacher) {
        return new SchoolClassRequestApiDto(
                "3B",
                idTeacher,
                100L
        );
    }

    public SchoolClassRequestApiDto prepareSchoolClassRequest(final Long idTeacher, final Long idSchool) {
        return new SchoolClassRequestApiDto(
                "3B",
                idTeacher,
                idSchool
        );
    }
}