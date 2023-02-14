package pl.edziennik.eDziennik.domain.school;

import pl.edziennik.eDziennik.domain.school.dto.SchoolRequestApiDto;

/**
 * Util class for school integration tests {@link SchoolIntegrationTest}
 */
public class SchoolIntegrationTestUtil {

    public SchoolRequestApiDto prepareSchoolRequestApi() {
        return new SchoolRequestApiDto(
                "asdasd",
                "ZXC",
                "123",
                "xcvxcv",
                "89234",
                "5352",
                "123553",
                1L
        );
    }

    public SchoolRequestApiDto prepareSchoolRequestApi(final String name, final String nip, final String regon) {
        return new SchoolRequestApiDto(
                name,
                "ZXCXq",
                "11232",
                "xcv1xcvc",
                nip,
                regon,
                "11232553",
                1L
        );
    }

    public SchoolRequestApiDto prepareSchoolRequestApi(final Long idSchoolLevel){
        return new SchoolRequestApiDto(
                "1B",
                "ZXC",
                "123",
                "xcvxcv",
                "89234",
                "5352",
                "123553",
                idSchoolLevel
        );
    }


}
