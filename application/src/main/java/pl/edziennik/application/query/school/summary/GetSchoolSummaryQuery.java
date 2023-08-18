package pl.edziennik.application.query.school.summary;

import org.springframework.data.domain.Pageable;
import pl.edziennik.application.common.dispatcher.base.HandledBy;
import pl.edziennik.application.common.dispatcher.query.IQuery;
import pl.edziennik.common.view.PageView;
import pl.edziennik.common.view.school.SchoolSummaryView;

/**
 * A query used for getting the paginated school list (list contains only part of school data)
 * <br>
 * <b>Return DTO: {@link SchoolSummaryView}</b>
 */
@HandledBy(handler = GetSchoolSummaryQueryHandler.class)
public record GetSchoolSummaryQuery(

        Pageable pageable

) implements IQuery<PageView<SchoolSummaryView>> {
}
