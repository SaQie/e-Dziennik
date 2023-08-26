package pl.edziennik.application.query.classroomschedule.classroomschedulesummary;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;
import pl.edziennik.application.common.dispatcher.base.HandledBy;
import pl.edziennik.application.common.dispatcher.query.IQuery;
import pl.edziennik.common.valueobject.id.ClassRoomId;
import pl.edziennik.common.view.PageView;
import pl.edziennik.common.view.classroomschedule.summary.ClassRoomScheduleSummaryView;

/**
 * A query used for getting the selected class-room schedule
 * <br>
 * <b>Return VIEW: {@link ClassRoomScheduleSummaryView}</b>
 */
@HandledBy(handler = GetClassRoomScheduleSummaryQueryHandler.class)
public record GetClassRoomScheduleSummaryQuery(

        @Valid @NotNull(message = "${field.empty}") ClassRoomId classRoomId,
        Pageable pageable

) implements IQuery<PageView<ClassRoomScheduleSummaryView>> {
}
