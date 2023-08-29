package pl.edziennik.application.query.teacherschedule;

import org.springframework.data.domain.Pageable;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.valueobject.id.TeacherId;
import pl.edziennik.common.view.PageView;
import pl.edziennik.common.view.teacherschedule.summary.TeacherScheduleSummaryView;
import pl.edziennik.common.view.teacherschedule.summaryforschool.TeacherScheduleSummaryForSchoolView;

public interface TeacherScheduleQueryDao {

    PageView<TeacherScheduleSummaryView> getTeacherSchedulesSummaryView(Pageable pageable, TeacherId teacherId);

    PageView<TeacherScheduleSummaryForSchoolView> getTeacherSchedulesSummaryViewForSchool(Pageable pageable, SchoolId schoolId);

}
