package pl.edziennik.eDziennik.subject;

import pl.edziennik.eDziennik.server.subject.domain.dto.SubjectRequestApiDto;

/**
 * Util class for Subject integration tests {@link SubjectIntegrationTest}
 */
class SubjectIntegrationTestUtil {

    protected SubjectRequestApiDto prepareSubjectRequestDto(final Long idTeacher){
        return new SubjectRequestApiDto(
                "Przyroda",
                "Nauka o przyrodzie",
                idTeacher
        );
    }

    protected SubjectRequestApiDto prepareSubjectRequestDto(final String name,final Long idTeacher){
        return new SubjectRequestApiDto(
                "Przyroda",
                "Nauka o przyrodzie",
                idTeacher
        );
    }



}
