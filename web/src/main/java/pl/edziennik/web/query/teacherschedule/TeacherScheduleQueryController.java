package pl.edziennik.web.query.teacherschedule;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.edziennik.application.common.dispatcher.Dispatcher;
import pl.edziennik.application.query.teacherschedule.summaryforschool.GetTeacherScheduleSummaryForSchoolQuery;
import pl.edziennik.application.query.teacherschedule.teacherchedulessummary.GetTeacherSchedulesSummaryQuery;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.valueobject.id.TeacherId;
import pl.edziennik.common.view.PageView;
import pl.edziennik.common.view.teacherschedule.summary.TeacherScheduleSummaryView;
import pl.edziennik.common.view.teacherschedule.summaryforschool.TeacherScheduleSummaryForSchoolView;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class TeacherScheduleQueryController {

    private final Dispatcher dispatcher;

    @GetMapping("/teachers/{teacherId}/schedules")
    @ResponseStatus(HttpStatus.OK)
    public PageView<TeacherScheduleSummaryView> getTeacherSchedule(Pageable pageable, @PathVariable TeacherId teacherId) {
        GetTeacherSchedulesSummaryQuery query = new GetTeacherSchedulesSummaryQuery(pageable, teacherId);

        return dispatcher.dispatch(query);
    }

    @GetMapping("/schools/{schoolId}/teachers/schedules")
    @ResponseStatus(HttpStatus.OK)
    public PageView<TeacherScheduleSummaryForSchoolView> getTeachersScheduleForSchool(Pageable pageable, @PathVariable SchoolId schoolId) {
        GetTeacherScheduleSummaryForSchoolQuery query = new GetTeacherScheduleSummaryForSchoolQuery(schoolId, pageable);

        return dispatcher.dispatch(query);
    }

}
