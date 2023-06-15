package pl.edziennik.application.query.school.config;

import jakarta.validation.constraints.NotNull;
import pl.edziennik.application.common.dispatcher.base.HandledBy;
import pl.edziennik.application.common.dispatcher.query.IQuery;
import pl.edziennik.common.dto.school.config.SchoolConfigSummaryDto;
import pl.edziennik.common.valueobject.id.SchoolId;

/**
 * A query used for getting school config parameter values
 * <br>
 * <b>Return DTO: {@link pl.edziennik.common.dto.school.config.SchoolConfigSummaryDto}</b>
 */
@HandledBy(handler = GetSchoolConfigSummaryQueryHandler.class)
public record GetSchoolConfigSummaryQuery(
        @NotNull(message = "{school.empty}") SchoolId schoolId
) implements IQuery<SchoolConfigSummaryDto> {

    public static final String SCHOOL_ID = "schoolId";

}
