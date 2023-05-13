package pl.edziennik.common.dto.school;

import pl.edziennik.common.valueobject.Name;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.valueobject.id.SchoolLevelId;

public record SchoolSummaryDto(

        SchoolId schoolId,
        Name name,
        SchoolLevelId schoolLevelId,
        Name schoolLevelName

) {

}