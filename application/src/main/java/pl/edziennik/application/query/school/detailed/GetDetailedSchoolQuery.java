package pl.edziennik.application.query.school.detailed;

import jakarta.validation.constraints.NotNull;
import pl.edziennik.application.common.dispatcher.base.HandledBy;
import pl.edziennik.application.common.dispatcher.query.IQuery;
import pl.edziennik.common.view.school.DetailedSchoolView;
import pl.edziennik.common.valueobject.id.SchoolId;

/**
 * A query used for getting the detailed school information
 * <br>
 * <b>Return DTO: {@link DetailedSchoolView}</b>
 */
@HandledBy(handler = GetDetailedSchoolQueryHandler.class)
public record GetDetailedSchoolQuery(

        @NotNull(message = "${school.empty}") SchoolId schoolId

) implements IQuery<DetailedSchoolView> {

    public static final String SCHOOL_ID = "schoolId";

}
