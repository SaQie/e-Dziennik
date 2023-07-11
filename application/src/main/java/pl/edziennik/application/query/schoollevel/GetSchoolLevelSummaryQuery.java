package pl.edziennik.application.query.schoollevel;

import pl.edziennik.application.common.dispatcher.base.HandledBy;
import pl.edziennik.application.common.dispatcher.query.IQuery;
import pl.edziennik.common.dto.schoollevel.SchoolLevelDto;

import java.util.List;

@HandledBy(handler = GetSchoolLevelSummaryQueryHandler.class)
public record GetSchoolLevelSummaryQuery(

) implements IQuery<List<SchoolLevelDto>> {
}
