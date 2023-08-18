package pl.edziennik.common.view.school.config;

import pl.edziennik.common.enums.AverageType;
import pl.edziennik.common.valueobject.id.SchoolConfigurationId;
import pl.edziennik.common.valueobject.id.SchoolId;

import java.io.Serializable;

public record SchoolConfigSummaryView(
        SchoolId schoolId,
        SchoolConfigurationId schoolConfigurationId,
        AverageType averageType
) implements Serializable {
}
