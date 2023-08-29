package pl.edziennik.application.query.teacherschedule.summaryforschool;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.IQueryHandler;
import pl.edziennik.common.view.PageView;
import pl.edziennik.common.view.teacherschedule.summaryforschool.TeacherScheduleSummaryForSchoolView;
import pl.edziennik.infrastructure.repository.teacherschedule.TeacherScheduleQueryRepository;

@Component
@AllArgsConstructor
class GetTeacherScheduleSummaryForSchoolQueryHandler implements IQueryHandler<GetTeacherScheduleSummaryForSchoolQuery, PageView<TeacherScheduleSummaryForSchoolView>> {

    private final TeacherScheduleQueryRepository teacherScheduleQueryRepository;

    @Override
    public PageView<TeacherScheduleSummaryForSchoolView> handle(GetTeacherScheduleSummaryForSchoolQuery command) {
        Page<TeacherScheduleSummaryForSchoolView> view = teacherScheduleQueryRepository.getTeachersSchedulesForSchoolView(command.pageable(), command.schoolId());

        if (view.isEmpty()) {
            return PageView.fromPage(view);
        }

        Page<TeacherScheduleSummaryForSchoolView> result = view.map(v -> new TeacherScheduleSummaryForSchoolView(
                v.teacherId(),
                v.fullName(),
                teacherScheduleQueryRepository.getTeacherSchedulesView(v.teacherId())
        ));

        return PageView.fromPage(result);
    }

}
