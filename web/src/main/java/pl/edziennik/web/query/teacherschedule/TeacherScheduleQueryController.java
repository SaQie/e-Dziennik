package pl.edziennik.web.query.teacherschedule;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.edziennik.application.query.teacherschedule.TeacherScheduleQueryDao;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.valueobject.id.TeacherId;
import pl.edziennik.common.view.PageView;
import pl.edziennik.common.view.teacherschedule.summary.TeacherScheduleSummaryView;
import pl.edziennik.common.view.teacherschedule.summaryforschool.TeacherScheduleSummaryForSchoolView;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "Teacher API")
public class TeacherScheduleQueryController {

    private final TeacherScheduleQueryDao dao;

    @Operation(summary = "Get given teacher schedules",
            description = "This API endpoint returns a paginated teacher schedules data")
    @GetMapping("/teachers/{teacherId}/schedules")
    @ResponseStatus(HttpStatus.OK)
    public PageView<TeacherScheduleSummaryView> getTeacherSchedule(Pageable pageable, @PathVariable TeacherId teacherId) {
        return dao.getTeacherSchedulesSummaryView(pageable, teacherId);
    }

    @Operation(summary = "Get given school teachers schedules",
            description = "This API endpoint returns a paginated teachers schedules data for given schoolId")
    @GetMapping("/schools/{schoolId}/teachers/schedules")
    @ResponseStatus(HttpStatus.OK)
    public PageView<TeacherScheduleSummaryForSchoolView> getTeachersScheduleForSchool(Pageable pageable, @PathVariable SchoolId schoolId) {
        return dao.getTeacherSchedulesSummaryViewForSchool(pageable, schoolId);
    }

}
