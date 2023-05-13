package pl.edziennik.application.query.schoolclass.detailed;

import pl.edziennik.application.common.dispatcher.base.HandledBy;
import pl.edziennik.application.common.dispatcher.query.IQuery;
import pl.edziennik.common.dto.schoolclass.DetailedSchoolClassDto;
import pl.edziennik.common.valueobject.id.SchoolClassId;

@HandledBy(handler = GetDetailedSchoolClassQueryHandler.class)
public record GetDetailedSchoolClassQuery(

        SchoolClassId schoolClassId

) implements IQuery<DetailedSchoolClassDto> {

    public static final String SCHOOL_CLASS_ID = "schoolClassId";

}
