package pl.edziennik.application.query.classroomschedule.classroomschedulesummary;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.IQueryHandler;
import pl.edziennik.common.view.PageView;
import pl.edziennik.common.view.classroomschedule.summary.ClassRoomScheduleSummaryView;
import pl.edziennik.infrastructure.repository.classroomschedule.ClassRoomScheduleQueryRepository;

@Component
@AllArgsConstructor
class GetClassRoomScheduleSummaryQueryHandler implements IQueryHandler<GetClassRoomScheduleSummaryQuery, PageView<ClassRoomScheduleSummaryView>> {

    private final ClassRoomScheduleQueryRepository classRoomScheduleQueryRepository;

    @Override
    public PageView<ClassRoomScheduleSummaryView> handle(GetClassRoomScheduleSummaryQuery command) {
        Page<ClassRoomScheduleSummaryView> view = classRoomScheduleQueryRepository.getClassRoomScheduleView(command.pageable(), command.classRoomId());

        return PageView.fromPage(view);
    }
}
