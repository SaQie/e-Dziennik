package pl.edziennik.application.query.schoolclass.summary;

import org.springframework.data.domain.Pageable;
import pl.edziennik.application.common.dispatcher.base.HandledBy;
import pl.edziennik.application.common.dispatcher.query.IQuery;
import pl.edziennik.common.dto.PageDto;
import pl.edziennik.common.dto.schoolclass.SchoolClassSummaryForSchoolDto;
import pl.edziennik.common.valueobject.id.SchoolId;

@HandledBy(handler = GetSchoolClassSummaryForSchoolQueryHandler.class)
public record GetSchoolClassSummaryForSchoolQuery(

        SchoolId schoolId,
        Pageable pageable

) implements IQuery<PageDto<SchoolClassSummaryForSchoolDto>> {
}
