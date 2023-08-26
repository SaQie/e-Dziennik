package pl.edziennik.application.query.teacherschedule.teacherchedulessummary;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;
import pl.edziennik.application.common.dispatcher.base.HandledBy;
import pl.edziennik.application.common.dispatcher.query.IQuery;
import pl.edziennik.common.valueobject.id.TeacherId;
import pl.edziennik.common.view.PageView;
import pl.edziennik.common.view.teacherschedule.summary.TeacherScheduleSummaryView;

/**
 * A query used for getting the selected teacher schedule
 * <br>
 * <b>Return VIEW: {@link TeacherScheduleSummaryView}</b>
 */
@HandledBy(handler = GetTeacherSchedulesSummaryQueryHandler.class)
public record GetTeacherSchedulesSummaryQuery(

        Pageable pageable,
        @Valid @NotNull(message = "${teacher.empty}") TeacherId teacherId

) implements IQuery<PageView<TeacherScheduleSummaryView>> {
}
