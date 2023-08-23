package pl.edziennik.application.query.classroomschedule.summaryforschool;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;
import pl.edziennik.application.common.dispatcher.base.HandledBy;
import pl.edziennik.application.common.dispatcher.query.IQuery;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.view.PageView;
import pl.edziennik.common.view.classroomschedule.summaryforschool.ClassRoomScheduleSummaryForSchoolView;

/**
 * A query used for getting all class-rooms in school schedules
 * <br>
 * <b>Return DTO: {@link ClassRoomScheduleSummaryForSchoolView}</b>
 */
@HandledBy(handler = GetClassRoomsScheduleSummaryForSchoolQueryHandler.class)
public record GetClassRoomsScheduleSummaryForSchoolQuery(

        @Valid @NotNull(message = "${school.empty}") SchoolId schoolId,
        Pageable pageable

) implements IQuery<PageView<ClassRoomScheduleSummaryForSchoolView>> {
}
