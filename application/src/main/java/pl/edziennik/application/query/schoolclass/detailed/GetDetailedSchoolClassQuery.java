package pl.edziennik.application.query.schoolclass.detailed;

import jakarta.validation.constraints.NotNull;
import pl.edziennik.application.common.dispatcher.HandledBy;
import pl.edziennik.application.common.dispatcher.IQuery;
import pl.edziennik.common.view.schoolclass.DetailedSchoolClassView;
import pl.edziennik.common.valueobject.id.SchoolClassId;

/**
 * A query used for getting the detailed school class information
 * <br>
 * <b>Return DTO: {@link DetailedSchoolClassView}</b>
 */
@HandledBy(handler = GetDetailedSchoolClassQueryHandler.class)
public record GetDetailedSchoolClassQuery(

        @NotNull(message = "{schoolClass.empty}") SchoolClassId schoolClassId

) implements IQuery<DetailedSchoolClassView> {

    public static final String SCHOOL_CLASS_ID = "schoolClassId";

}
