package pl.edziennik.application.query.schoolclass.config;

import jakarta.validation.constraints.NotNull;
import pl.edziennik.application.common.dispatcher.HandledBy;
import pl.edziennik.application.common.dispatcher.IQuery;
import pl.edziennik.common.view.schoolclass.config.SchoolClassConfigSummaryView;
import pl.edziennik.common.valueobject.id.SchoolClassId;

/**
 * A query used for getting the school class configuration parameter values
 * <br>
 * <b>Return DTO: {@link SchoolClassConfigSummaryView}</b>
 */
@HandledBy(handler = GetSchoolClassConfigSummaryQueryHandler.class)
public record GetSchoolClassConfigSummaryQuery(

        @NotNull(message = "{schoolClass.empty}") SchoolClassId schoolClassId

) implements IQuery<SchoolClassConfigSummaryView> {

    public static final String SCHOOL_CLASS_ID = "schoolClassId";

}
