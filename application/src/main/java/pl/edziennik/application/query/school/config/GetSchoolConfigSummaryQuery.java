package pl.edziennik.application.query.school.config;

import jakarta.validation.constraints.NotNull;
import pl.edziennik.application.common.dispatcher.HandledBy;
import pl.edziennik.application.common.dispatcher.IQuery;
import pl.edziennik.common.view.school.config.SchoolConfigSummaryView;
import pl.edziennik.common.valueobject.id.SchoolId;

/**
 * A query used for getting school config parameter values
 * <br>
 * <b>Return DTO: {@link SchoolConfigSummaryView}</b>
 */
@HandledBy(handler = GetSchoolConfigSummaryQueryHandler.class)
public record GetSchoolConfigSummaryQuery(
        @NotNull(message = "{school.empty}") SchoolId schoolId
) implements IQuery<SchoolConfigSummaryView> {

    public static final String SCHOOL_ID = "schoolId";

}
