package pl.edziennik.common.view.schoolclass;

import pl.edziennik.common.valueobject.Name;
import pl.edziennik.common.valueobject.id.SchoolClassId;

import java.io.Serializable;

public record SchoolClassSummaryForSchoolView(
        SchoolClassId schoolClassId,
        Name schoolClassName
) implements Serializable {
}
