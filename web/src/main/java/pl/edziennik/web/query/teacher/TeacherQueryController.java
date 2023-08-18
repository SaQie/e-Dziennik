package pl.edziennik.web.query.teacher;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.edziennik.application.common.dispatcher.Dispatcher;
import pl.edziennik.application.query.teacher.detailed.GetDetailedTeacherQuery;
import pl.edziennik.application.query.teacher.subjects.GetTeacherSubjectsSummaryQuery;
import pl.edziennik.application.query.teacher.summary.GetTeacherSummaryQuery;
import pl.edziennik.common.view.PageView;
import pl.edziennik.common.view.teacher.DetailedTeacherView;
import pl.edziennik.common.view.teacher.TeacherSubjectsSummaryView;
import pl.edziennik.common.view.teacher.TeacherSummaryView;
import pl.edziennik.common.valueobject.id.TeacherId;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/teachers")
public class TeacherQueryController {

    private final Dispatcher dispatcher;

    @GetMapping("/{teacherId}")
    @ResponseStatus(HttpStatus.OK)
    public DetailedTeacherView getTeacherById(@PathVariable TeacherId teacherId) {
        GetDetailedTeacherQuery query = new GetDetailedTeacherQuery(teacherId);

        return dispatcher.dispatch(query);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public PageView<TeacherSummaryView> getAllTeachers(Pageable pageable) {
        GetTeacherSummaryQuery query = new GetTeacherSummaryQuery(pageable);

        return dispatcher.dispatch(query);
    }

    @GetMapping("/{teacherId}/subjects")
    @ResponseStatus(HttpStatus.OK)
    public PageView<TeacherSubjectsSummaryView> getTeacherSubjectsSummary(@PathVariable TeacherId teacherId, Pageable pageable) {
        GetTeacherSubjectsSummaryQuery query = new GetTeacherSubjectsSummaryQuery(pageable, teacherId);

        return dispatcher.dispatch(query);
    }


}
