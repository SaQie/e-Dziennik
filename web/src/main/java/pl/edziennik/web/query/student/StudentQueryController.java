package pl.edziennik.web.query.student;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.edziennik.application.common.dispatcher.Dispatcher;
import pl.edziennik.application.query.student.detailed.GetDetailedStudentQuery;
import pl.edziennik.application.query.student.summary.GetStudentSummaryQuery;
import pl.edziennik.common.view.PageView;
import pl.edziennik.common.view.student.DetailedStudentView;
import pl.edziennik.common.view.student.StudentSummaryView;
import pl.edziennik.common.valueobject.id.StudentId;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/students")
public class StudentQueryController {

    private final Dispatcher dispatcher;

    @GetMapping("/{studentId}")
    @ResponseStatus(HttpStatus.OK)
    public DetailedStudentView getStudentById(@PathVariable StudentId studentId) {
        GetDetailedStudentQuery getDetailedStudentQuery = new GetDetailedStudentQuery(studentId);

        return dispatcher.dispatch(getDetailedStudentQuery);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PageView<StudentSummaryView> getAllStudents(Pageable pageable) {
        GetStudentSummaryQuery getStudentSummaryQuery = new GetStudentSummaryQuery(pageable);

        return dispatcher.dispatch(getStudentSummaryQuery);
    }

}
