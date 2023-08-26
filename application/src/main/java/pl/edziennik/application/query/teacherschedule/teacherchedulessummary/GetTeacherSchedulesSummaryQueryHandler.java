package pl.edziennik.application.query.teacherschedule.teacherchedulessummary;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.query.IQueryHandler;
import pl.edziennik.common.view.PageView;
import pl.edziennik.common.view.teacherschedule.summary.TeacherScheduleSummaryView;
import pl.edziennik.infrastructure.repository.teacherschedule.TeacherScheduleQueryRepository;

@Component
@AllArgsConstructor
class GetTeacherSchedulesSummaryQueryHandler implements IQueryHandler<GetTeacherSchedulesSummaryQuery, PageView<TeacherScheduleSummaryView>> {

    private final TeacherScheduleQueryRepository teacherScheduleQueryRepository;

    @Override
    public PageView<TeacherScheduleSummaryView> handle(GetTeacherSchedulesSummaryQuery command) {
        Page<TeacherScheduleSummaryView> view = teacherScheduleQueryRepository.getTeacherScheduleView(command.pageable(), command.teacherId());

        return PageView.fromPage(view);
    }
}
