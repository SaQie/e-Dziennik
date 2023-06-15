package pl.edziennik.common.dto.school.config;

import pl.edziennik.common.enums.AverageType;
import pl.edziennik.common.valueobject.id.SchoolConfigurationId;
import pl.edziennik.common.valueobject.id.SchoolId;

public record SchoolConfigSummaryDto(
        SchoolId schoolId,
        SchoolConfigurationId schoolConfigurationId,
        AverageType averageType
) {
}
