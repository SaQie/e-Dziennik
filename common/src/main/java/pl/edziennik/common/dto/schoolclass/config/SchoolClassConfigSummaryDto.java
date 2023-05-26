package pl.edziennik.common.dto.schoolclass.config;

import pl.edziennik.common.valueobject.Name;
import pl.edziennik.common.valueobject.id.SchoolClassId;

public record SchoolClassConfigSummaryDto(
        SchoolClassId schoolClassId,
        Name schoolClassName,
        Integer maxStudentsSize,
        Boolean autoAssignSubjects
) {
}
