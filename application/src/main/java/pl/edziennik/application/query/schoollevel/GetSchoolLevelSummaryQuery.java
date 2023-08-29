package pl.edziennik.application.query.schoollevel;

import pl.edziennik.application.common.dispatcher.HandledBy;
import pl.edziennik.application.common.dispatcher.IQuery;
import pl.edziennik.common.view.schoollevel.SchoolLevelView;

import java.util.List;

@HandledBy(handler = GetSchoolLevelSummaryQueryHandler.class)
public record GetSchoolLevelSummaryQuery(

) implements IQuery<List<SchoolLevelView>> {
}
