package pl.edziennik.common.view.teacher;

import pl.edziennik.common.valueobject.vo.Description;
import pl.edziennik.common.valueobject.vo.Name;
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
