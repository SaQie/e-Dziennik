package pl.edziennik.application.query.schoolclass.summary;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;
import pl.edziennik.application.common.dispatcher.base.HandledBy;
import pl.edziennik.application.common.dispatcher.query.IQuery;
import pl.edziennik.common.dto.PageDto;
import pl.edziennik.common.dto.schoolclass.SchoolClassSummaryForSchoolDto;
import pl.edziennik.common.valueobject.id.SchoolId;


/**
 * A query used for getting the paginated school classes list for given school (list contains only part of school class data)
 * <br>
 * <b>Return DTO: {@link SchoolClassSummaryForSchoolDto}</b>
 */
@HandledBy(handler = GetSchoolClassSummaryForSchoolQueryHandler.class)
public record GetSchoolClassSummaryForSchoolQuery(

        @NotNull(message = "{school.empty}") SchoolId schoolId,
        Pageable pageable

) implements IQuery<PageDto<SchoolClassSummaryForSchoolDto>> {
}
