package pl.edziennik.common.view.teacher;

import pl.edziennik.common.valueobject.vo.FullName;
import pl.edziennik.common.valueobject.vo.Name;
import pl.edziennik.common.valueobject.vo.Username;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.valueobject.id.TeacherId;
import pl.edziennik.common.valueobject.id.UserId;

import java.io.Serializable;

public record TeacherSummaryView(

        TeacherId teacherId,
        UserId userId,
        Username username,
        FullName fullName,
        SchoolId schoolId,
        Name schoolName

) implements Serializable {
}
