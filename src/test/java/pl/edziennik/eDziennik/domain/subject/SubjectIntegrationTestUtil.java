package pl.edziennik.eDziennik.domain.subject;

import pl.edziennik.eDziennik.domain.schoolclass.domain.wrapper.SchoolClassId;
import pl.edziennik.eDziennik.domain.subject.dto.SubjectRequestApiDto;
import pl.edziennik.eDziennik.domain.teacher.domain.wrapper.TeacherId;

/**
 * Util class for Subject integration tests {@link SubjectIntegrationTest}
 */
public class SubjectIntegrationTestUtil {

    public SubjectRequestApiDto prepareSubjectRequestDto(final TeacherId teacherId){
        return new SubjectRequestApiDto(
                "Przyroda",
                "Nauka o przyrodzie",
                teacherId,
                SchoolClassId.wrap(100L)
        );
    }

    public SubjectRequestApiDto prepareSubjectRequestDto(final String name,final TeacherId teacherId){
        return new SubjectRequestApiDto(
                name,
                "Nauka o przyrodzie",
                teacherId,
                SchoolClassId.wrap(100L)
        );
    }

    public SubjectRequestApiDto prepareSubjectRequestDto(final String name, final TeacherId teacherId, final SchoolClassId schoolClassId){
        return new SubjectRequestApiDto(
                name,
                "Nauka o przyrodzie",
                teacherId,
                schoolClassId
        );
    }


}
