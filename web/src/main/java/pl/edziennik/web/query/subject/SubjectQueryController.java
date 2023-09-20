package pl.edziennik.web.query.subject;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.edziennik.application.query.subject.SubjectQueryDao;
import pl.edziennik.common.valueobject.id.StudentId;
import pl.edziennik.common.valueobject.id.SubjectId;
import pl.edziennik.common.view.grade.allsubjects.StudentAllSubjectsGradesHeaderView;
import pl.edziennik.common.view.grade.bysubject.StudentGradesBySubjectView;
import pl.edziennik.common.view.subject.DetailedSubjectView;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/subjects")
@Tag(name = "Subject API")
public class SubjectQueryController {

    private final SubjectQueryDao dao;

    @Operation(summary = "Get detailed information about given subject")
    @GetMapping("/{subjectId}")
    @ResponseStatus(HttpStatus.OK)
    public DetailedSubjectView getSubject(@PathVariable SubjectId subjectId) {
        return dao.getDetailedSubjectView(subjectId);
    }

    @Operation(summary = "Get all students grades for given subject")
    @GetMapping("/{subjectId}/students/grades")
    @ResponseStatus(HttpStatus.OK)
    public List<StudentGradesBySubjectView> getStudentGradesBySubject(@PathVariable SubjectId subjectId) {
        return dao.getAllStudentGradesBySubjectView(subjectId);
    }

    @Operation(summary = "Get all given student grades for given subject")
    @GetMapping("/{subjectId}/students/{studentId}/grades")
    @ResponseStatus(HttpStatus.OK)
    public StudentGradesBySubjectView getGradesOfSpecificStudentBySubject(@PathVariable SubjectId subjectId, @PathVariable StudentId studentId) {
        return dao.getStudentGradesBySubjectView(subjectId, studentId);
    }

    @Operation(summary = "Get all given student grades for all subjects")
    @GetMapping("/students/{studentId}/grades")
    @ResponseStatus(HttpStatus.OK)
    public StudentAllSubjectsGradesHeaderView getAllSubjectsGradesOfSpecificStudent(@PathVariable StudentId studentId) {
        return dao.getAllSubjectsGradesOfSpecificStudentView(studentId);
    }


}
