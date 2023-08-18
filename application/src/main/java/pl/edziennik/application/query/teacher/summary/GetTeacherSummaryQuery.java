package pl.edziennik.application.query.teacher.summary;

import org.springframework.data.domain.Pageable;
import pl.edziennik.application.common.dispatcher.base.HandledBy;
import pl.edziennik.application.common.dispatcher.query.IQuery;
import pl.edziennik.common.view.PageView;
import pl.edziennik.common.view.teacher.TeacherSummaryView;

/**
 * A query used for getting the paginated teachers list (list contains only part of teachers data)
 * <br>
 * <b>Return DTO: {@link TeacherSummaryView}</b>
 */
@HandledBy(handler = GetTeacherSummaryQueryHandler.class)
public record GetTeacherSummaryQuery(

        Pageable pageable

) implements IQuery<PageView<TeacherSummaryView>> {
}

