package pl.edziennik.common.view.school;

import pl.edziennik.common.valueobject.vo.FullName;
import pl.edziennik.common.valueobject.vo.Name;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.valueobject.id.SchoolLevelId;

import java.io.Serializable;

public record SchoolSummaryView(

        SchoolId schoolId,
        Name name,
        SchoolLevelId schoolLevelId,
        Name schoolLevelName,
        FullName directorName

) implements Serializable {

}
