package pl.edziennik.web.query.teacher;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.edziennik.application.query.teacher.TeacherQueryDao;
import pl.edziennik.common.valueobject.id.TeacherId;
import pl.edziennik.common.view.PageView;
import pl.edziennik.common.view.teacher.DetailedTeacherView;
import pl.edziennik.common.view.teacher.TeacherSubjectsSummaryView;
import pl.edziennik.common.view.teacher.TeacherSummaryView;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/teachers")
@Tag(name = "Teacher API")
public class TeacherQueryController {

    private final TeacherQueryDao dao;

    @Operation(summary = "Get detailed information about given teacher")
    @GetMapping("/{teacherId}")
    @ResponseStatus(HttpStatus.OK)
    public DetailedTeacherView getTeacherById(@PathVariable TeacherId teacherId) {
        return dao.getDetailedTeacherView(teacherId);
    }

    @Operation(summary = "Get list of all teachers")
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public PageView<TeacherSummaryView> getAllTeachers(Pageable pageable) {
        return dao.getTeacherSummaryView(pageable);
    }

    @Operation(summary = "Get a list of subjects that the given teacher teaches")
    @GetMapping("/{teacherId}/subjects")
    @ResponseStatus(HttpStatus.OK)
    public PageView<TeacherSubjectsSummaryView> getTeacherSubjectsSummary(@PathVariable TeacherId teacherId, Pageable pageable) {
        return dao.getTeacherSubjectsSummaryView(pageable, teacherId);
    }


}
