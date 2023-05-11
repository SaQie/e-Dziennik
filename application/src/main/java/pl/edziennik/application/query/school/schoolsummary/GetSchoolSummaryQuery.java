package pl.edziennik.application.query.school.schoolsummary;

import org.springframework.data.domain.Pageable;
import pl.edziennik.application.common.dispatcher.base.HandledBy;
import pl.edziennik.application.common.dispatcher.query.IQuery;
import pl.edziennik.common.dto.PageDto;
import pl.edziennik.common.dto.school.SchoolSummaryDto;

@HandledBy(handler = GetSchoolSummaryQueryHandler.class)
public record GetSchoolSummaryQuery(
        Pageable pageable
) implements IQuery<PageDto<SchoolSummaryDto>> {
}
