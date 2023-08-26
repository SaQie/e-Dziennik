package pl.edziennik.common.view.teacherschedule.summaryforschool;

import pl.edziennik.common.valueobject.id.TeacherId;
import pl.edziennik.common.valueobject.vo.FullName;
import pl.edziennik.common.view.teacherschedule.summary.TeacherScheduleSummaryView;

import java.util.Collections;
import java.util.List;

public record TeacherScheduleSummaryForSchoolView(
        TeacherId teacherId,
        FullName fullName,
        List<TeacherScheduleSummaryView> schedules
) {

    public TeacherScheduleSummaryForSchoolView(TeacherId teacherId, FullName fullName) {
        this(teacherId, fullName, Collections.emptyList());
    }

    public TeacherScheduleSummaryForSchoolView(TeacherScheduleSummaryForSchoolView view, List<TeacherScheduleSummaryView> schedules) {
        this(view.teacherId, view.fullName, schedules);
    }
}
