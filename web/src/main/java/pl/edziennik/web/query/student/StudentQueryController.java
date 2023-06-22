package pl.edziennik.web.query.student;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.edziennik.application.common.dispatcher.Dispatcher;
import pl.edziennik.application.query.student.detailed.GetDetailedStudentQuery;
import pl.edziennik.application.query.student.summary.GetStudentSummaryQuery;
import pl.edziennik.common.dto.PageDto;
import pl.edziennik.common.dto.student.DetailedStudentDto;
import pl.edziennik.common.dto.student.StudentSummaryDto;
import pl.edziennik.common.valueobject.id.StudentId;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/students")
public class StudentQueryController {

    private final Dispatcher dispatcher;

    @GetMapping("/{studentId}")
    @ResponseStatus(HttpStatus.OK)
    public DetailedStudentDto getStudentById(@PathVariable StudentId studentId) {
        GetDetailedStudentQuery getDetailedStudentQuery = new GetDetailedStudentQuery(studentId);

        return dispatcher.dispatch(getDetailedStudentQuery);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Cacheable(value = "students", key = "#root.method.name")
    public PageDto<StudentSummaryDto> getAllStudents(Pageable pageable) {
        GetStudentSummaryQuery getStudentSummaryQuery = new GetStudentSummaryQuery(pageable);

        return dispatcher.dispatch(getStudentSummaryQuery);
    }

}
