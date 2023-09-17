package pl.edziennik.application.query.teacherschedule;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import pl.edziennik.common.cache.CacheValueConstants;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.valueobject.id.TeacherId;
import pl.edziennik.common.view.PageView;
import pl.edziennik.common.view.teacherschedule.summary.TeacherScheduleSummaryView;
import pl.edziennik.common.view.teacherschedule.summaryforschool.TeacherScheduleSummaryForSchoolView;
import pl.edziennik.infrastructure.repository.teacherschedule.TeacherScheduleQueryRepository;

@Repository
@AllArgsConstructor
class TeacherScheduleQueryDaoImpl implements TeacherScheduleQueryDao {

    private final TeacherScheduleQueryRepository teacherScheduleQueryRepository;

    @Override
    public PageView<TeacherScheduleSummaryView> getTeacherSchedulesSummaryView(Pageable pageable, TeacherId teacherId) {
        return PageView.fromPage(teacherScheduleQueryRepository.getTeacherScheduleView(pageable, teacherId));

    }

    @Override
    public PageView<TeacherScheduleSummaryForSchoolView> getTeacherSchedulesSummaryViewForSchool(Pageable pageable, SchoolId schoolId) {
        Page<TeacherScheduleSummaryForSchoolView> view = teacherScheduleQueryRepository.getTeachersSchedulesForSchoolView(pageable, schoolId);

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
