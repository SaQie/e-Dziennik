package pl.edziennik.web.query.subject;

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
public class SubjectQueryController {

    private final SubjectQueryDao dao;

    @GetMapping("/{subjectId}")
    @ResponseStatus(HttpStatus.OK)
    public DetailedSubjectView getSubject(@PathVariable SubjectId subjectId) {
        return dao.getDetailedSubjectView(subjectId);
    }

    @GetMapping("/{subjectId}/students/grades")
    @ResponseStatus(HttpStatus.OK)
    public List<StudentGradesBySubjectView> getStudentGradesBySubject(@PathVariable SubjectId subjectId) {
        return dao.getAllStudentGradesBySubjectView(subjectId);
    }

    @GetMapping("/{subjectId}/students/{studentId}/grades")
    @ResponseStatus(HttpStatus.OK)
    public StudentGradesBySubjectView getGradesOfSpecificStudentBySubject(@PathVariable SubjectId subjectId, @PathVariable StudentId studentId) {
        return dao.getStudentGradesBySubjectView(subjectId, studentId);
    }

    @GetMapping("/students/{studentId}/grades")
    @ResponseStatus(HttpStatus.OK)
    public StudentAllSubjectsGradesHeaderView getAllSubjectsGradesOfSpecificStudent(@PathVariable StudentId studentId) {
        return dao.getAllSubjectsGradesOfSpecificStudentView(studentId);
    }


}
