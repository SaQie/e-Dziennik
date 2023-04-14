package pl.edziennik.eDziennik.domain.schoolclass.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import pl.edziennik.eDziennik.domain.schoolclass.domain.wrapper.SchoolClassId;
import pl.edziennik.eDziennik.domain.subject.domain.wrapper.SubjectId;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor
public class SchoolClassAssignSubjectRequestApiDto {
    private final SchoolClassId schoolClassId;
    private final SubjectId subjectId;
}
