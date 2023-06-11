package pl.edziennik.application.query.schoolclass.detailed;

import jakarta.validation.constraints.NotNull;
import pl.edziennik.application.common.dispatcher.base.HandledBy;
import pl.edziennik.application.common.dispatcher.query.IQuery;
import pl.edziennik.common.dto.schoolclass.DetailedSchoolClassDto;
import pl.edziennik.common.valueobject.id.SchoolClassId;

/**
 * A query used for getting the detailed school class information
 * <br>
 * <b>Return DTO: {@link DetailedSchoolClassDto}</b>
 */
@HandledBy(handler = GetDetailedSchoolClassQueryHandler.class)
public record GetDetailedSchoolClassQuery(

        @NotNull(message = "{schoolClass.empty}") SchoolClassId schoolClassId

) implements IQuery<DetailedSchoolClassDto> {

    public static final String SCHOOL_CLASS_ID = "schoolClassId";

}
