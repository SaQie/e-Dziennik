package pl.edziennik.eDziennik.grade;

import pl.edziennik.eDziennik.domain.grade.dto.GradeRequestApiDto;

/**
 * Util class for Grade integration test {@link GradeIntegrationTest}
 */
public class GradeIntegrationTestUtil {

    public GradeRequestApiDto prepareRequestApi(final int grade, final int weight, final String description){
        return new GradeRequestApiDto(
                grade,
                weight,
                description
        );
    }

    public GradeRequestApiDto prepareRequestApi(final int grade, final int weight){
        return new GradeRequestApiDto(
                grade,
                weight,
                "TESTING"
        );
    }



}
