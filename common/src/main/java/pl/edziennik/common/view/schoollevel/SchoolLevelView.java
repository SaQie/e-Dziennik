package pl.edziennik.common.view.schoollevel;

import pl.edziennik.common.valueobject.Name;
import pl.edziennik.common.valueobject.id.SchoolLevelId;

public record SchoolLevelView(
        SchoolLevelId schoolLevelId,
        Name schoolLevelName
) {

}
