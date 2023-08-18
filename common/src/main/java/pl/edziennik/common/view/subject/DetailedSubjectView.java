package pl.edziennik.common.view.subject;

import pl.edziennik.common.valueobject.Description;
import pl.edziennik.common.valueobject.FullName;
import pl.edziennik.common.valueobject.Name;
import pl.edziennik.common.valueobject.id.SchoolClassId;
import pl.edziennik.common.valueobject.id.SubjectId;
import pl.edziennik.common.valueobject.id.TeacherId;

public record DetailedSubjectView(

        SubjectId subjectId,
        Name name,
        Description description,
        TeacherId teacherId,
        FullName teacherFullName,
        SchoolClassId schoolClassId,
        Name schoolClassName

) {
}
