package pl.edziennik.common.view.teacher;

import pl.edziennik.common.valueobject.Description;
import pl.edziennik.common.valueobject.Name;
import pl.edziennik.common.valueobject.id.SchoolClassId;
import pl.edziennik.common.valueobject.id.SubjectId;

import java.io.Serializable;

public record TeacherSubjectsSummaryView(

        SubjectId subjectId,
        Name name,
        Description description,
        SchoolClassId schoolClassId,
        Name schoolClassName

) implements Serializable {
}
