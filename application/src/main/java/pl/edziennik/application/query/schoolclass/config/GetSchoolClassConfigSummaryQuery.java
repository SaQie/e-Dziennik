package pl.edziennik.application.query.schoolclass.config;

import jakarta.validation.constraints.NotNull;
import pl.edziennik.application.common.dispatcher.base.HandledBy;
import pl.edziennik.application.common.dispatcher.query.IQuery;
import pl.edziennik.common.dto.schoolclass.config.SchoolClassConfigSummaryDto;
import pl.edziennik.common.valueobject.id.SchoolClassId;

/**
 * A query used for getting the school class configuration parameter values
 * <br>
 * <b>Return DTO: {@link SchoolClassConfigSummaryDto}</b>
 */
@HandledBy(handler = GetSchoolClassConfigSummaryQueryHandler.class)
public record GetSchoolClassConfigSummaryQuery(

        @NotNull(message = "{schoolClass.empty}") SchoolClassId schoolClassId

) implements IQuery<SchoolClassConfigSummaryDto> {

    public static final String SCHOOL_CLASS_ID = "schoolClassId";

}
