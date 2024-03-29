package pl.edziennik.web.query.student;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.edziennik.application.query.student.StudentQueryDao;
import pl.edziennik.common.valueobject.id.StudentId;
import pl.edziennik.common.view.PageView;
import pl.edziennik.common.view.student.DetailedStudentView;
import pl.edziennik.common.view.student.StudentSummaryView;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/students")
@Tag(name = "Student API")
public class StudentQueryController {

    private final StudentQueryDao dao;

    @Operation(summary = "Get detailed information about given student")
    @GetMapping("/{studentId}")
    @ResponseStatus(HttpStatus.OK)
    public DetailedStudentView getStudentById(@PathVariable StudentId studentId) {
        return dao.getDetailedStudentView(studentId);
    }

    @Operation(summary = "Get list of all students")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PageView<StudentSummaryView> getAllStudents(Pageable pageable) {
        return dao.getStudentSummaryView(pageable);
    }

}
