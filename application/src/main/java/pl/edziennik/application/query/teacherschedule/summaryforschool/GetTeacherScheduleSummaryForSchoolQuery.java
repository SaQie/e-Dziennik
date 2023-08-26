package pl.edziennik.application.query.teacherschedule.summaryforschool;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;
import pl.edziennik.application.common.dispatcher.base.HandledBy;
import pl.edziennik.application.common.dispatcher.query.IQuery;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.view.PageView;
import pl.edziennik.common.view.teacherschedule.summaryforschool.TeacherScheduleSummaryForSchoolView;


/**
 * A query used for getting all teachers in school schedules
 * <br>
 * <b>Return VIEW: {@link TeacherScheduleSummaryForSchoolView}</b>
 */
@HandledBy(handler = GetTeacherScheduleSummaryForSchoolQueryHandler.class)
public record GetTeacherScheduleSummaryForSchoolQuery(

        @Valid @NotNull(message = "${school.empty}") SchoolId schoolId,
        Pageable pageable


) implements IQuery<PageView<TeacherScheduleSummaryForSchoolView>> {
}
