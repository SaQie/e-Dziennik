package pl.edziennik.common.dto.schoollevel;

import pl.edziennik.common.valueobject.Name;
import pl.edziennik.common.valueobject.id.SchoolLevelId;

public record SchoolLevelDto(
        SchoolLevelId schoolLevelId,
        Name schoolLevelName
) {

}
