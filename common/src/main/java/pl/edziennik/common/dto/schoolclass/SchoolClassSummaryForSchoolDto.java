package pl.edziennik.common.dto.schoolclass;

import pl.edziennik.common.valueobject.Name;
import pl.edziennik.common.valueobject.id.SchoolClassId;

public record SchoolClassSummaryForSchoolDto(
        SchoolClassId schoolClassId,
        Name schoolClassName
) {
}
