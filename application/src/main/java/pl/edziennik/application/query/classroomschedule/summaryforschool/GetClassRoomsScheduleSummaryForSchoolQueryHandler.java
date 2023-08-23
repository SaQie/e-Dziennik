package pl.edziennik.application.query.classroomschedule.summaryforschool;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.query.IQueryHandler;
import pl.edziennik.common.view.PageView;
import pl.edziennik.common.view.classroomschedule.summaryforschool.ClassRoomScheduleSummaryForSchoolView;
import pl.edziennik.infrastructure.repository.classroomschedule.ClassRoomScheduleQueryRepository;

@Component
@AllArgsConstructor
class GetClassRoomsScheduleSummaryForSchoolQueryHandler
        implements IQueryHandler<GetClassRoomsScheduleSummaryForSchoolQuery, PageView<ClassRoomScheduleSummaryForSchoolView>> {

    private final ClassRoomScheduleQueryRepository classRoomScheduleQueryRepository;

    @Override
    public PageView<ClassRoomScheduleSummaryForSchoolView> handle(GetClassRoomsScheduleSummaryForSchoolQuery command) {
        Page<ClassRoomScheduleSummaryForSchoolView> view = classRoomScheduleQueryRepository.getClassRoomScheduleForSchoolView(command.pageable(), command.schoolId());

        if (view.isEmpty()) {
            return PageView.fromPage(view);
        }

        // map to result page that contains list of schedules
        Page<ClassRoomScheduleSummaryForSchoolView> result = view.map(v -> new ClassRoomScheduleSummaryForSchoolView(
                v.classRoomId(),
                v.classRoomName(),
                classRoomScheduleQueryRepository.getClassRoomScheduleView(v.classRoomId())
        ));

        return PageView.fromPage(result);
    }
}
