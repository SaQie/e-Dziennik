package pl.edziennik.common.dto.schoolclass;

import pl.edziennik.common.valueobject.FullName;
import pl.edziennik.common.valueobject.id.StudentId;

public record StudentSummaryForSchoolClassDto(
        StudentId studentId,
        FullName fullName
) {

}
