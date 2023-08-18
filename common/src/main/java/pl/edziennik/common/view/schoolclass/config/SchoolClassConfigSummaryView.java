package pl.edziennik.common.view.schoolclass.config;

import pl.edziennik.common.valueobject.Name;
import pl.edziennik.common.valueobject.id.SchoolClassId;

public record SchoolClassConfigSummaryView(
        SchoolClassId schoolClassId,
        Name schoolClassName,
        Integer maxStudentsSize,
        Boolean autoAssignSubjects
) {
}
