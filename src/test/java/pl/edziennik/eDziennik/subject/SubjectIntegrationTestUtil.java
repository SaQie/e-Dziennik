package pl.edziennik.eDziennik.subject;

import pl.edziennik.eDziennik.server.subject.domain.dto.SubjectRequestApiDto;

/**
 * Util class for Subject integration tests {@link SubjectIntegrationTest}
 */
public class SubjectIntegrationTestUtil {

    public SubjectRequestApiDto prepareSubjectRequestDto(final Long idTeacher){
        return new SubjectRequestApiDto(
                "Przyroda",
                "Nauka o przyrodzie",
                idTeacher,
                100L
        );
    }

    public SubjectRequestApiDto prepareSubjectRequestDto(final String name,final Long idTeacher){
        return new SubjectRequestApiDto(
                name,
                "Nauka o przyrodzie",
                idTeacher,
                100L
        );
    }

    public SubjectRequestApiDto prepareSubjectRequestDto(final String name,final Long idTeacher, final Long idSchoolClass){
        return new SubjectRequestApiDto(
                name,
                "Nauka o przyrodzie",
                idTeacher,
                idSchoolClass
        );
    }


}
