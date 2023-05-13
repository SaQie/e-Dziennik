package pl.edziennik.application.query.school.detailed;

import pl.edziennik.application.common.dispatcher.base.HandledBy;
import pl.edziennik.application.common.dispatcher.query.IQuery;
import pl.edziennik.common.dto.school.DetailedSchoolDto;
import pl.edziennik.common.valueobject.id.SchoolId;

@HandledBy(handler = GetDetailedSchoolQueryHandler.class)
public record GetDetailedSchoolQuery(

        SchoolId schoolId

) implements IQuery<DetailedSchoolDto> {

    public static final String SCHOOL_ID = "schoolId";

}
