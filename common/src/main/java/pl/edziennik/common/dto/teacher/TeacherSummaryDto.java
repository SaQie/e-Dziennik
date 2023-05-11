package pl.edziennik.common.dto.teacher;

import pl.edziennik.common.valueobject.FullName;
import pl.edziennik.common.valueobject.Name;
import pl.edziennik.common.valueobject.Username;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.valueobject.id.TeacherId;
import pl.edziennik.common.valueobject.id.UserId;

public record TeacherSummaryDto(

        TeacherId teacherId,
        UserId userId,
        Username username,
        FullName fullName,
        SchoolId schoolId,
        Name schoolName

) {
}
