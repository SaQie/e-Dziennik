package pl.edziennik.web.query.subject;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.edziennik.application.common.dispatcher.Dispatcher;
import pl.edziennik.application.query.subject.detailed.GetDetailedSubjectQuery;
import pl.edziennik.application.query.subject.specificstudentgrades.GetGradesOfSpecificStudentBySubjectQuery;
import pl.edziennik.application.query.subject.studentsgrades.all.GetAllSubjectsGradesOfSpecificStudentQuery;
import pl.edziennik.application.query.subject.studentsgrades.bysubject.GetStudentsGradesBySubjectQuery;
import pl.edziennik.common.view.grade.allsubjects.StudentAllSubjectsGradesHeaderView;
import pl.edziennik.common.view.grade.bysubject.StudentGradesBySubjectView;
import pl.edziennik.common.view.subject.DetailedSubjectView;
import pl.edziennik.common.valueobject.id.StudentId;
import pl.edziennik.common.valueobject.id.SubjectId;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/subjects")
public class SubjectQueryController {

    private final Dispatcher dispatcher;

    @GetMapping("/{subjectId}")
    @ResponseStatus(HttpStatus.OK)
    public DetailedSubjectView getSubject(@PathVariable SubjectId subjectId) {
        GetDetailedSubjectQuery query = new GetDetailedSubjectQuery(subjectId);

        return dispatcher.dispatch(query);
    }

    @GetMapping("/{subjectId}/students/grades")
    @ResponseStatus(HttpStatus.OK)
    public List<StudentGradesBySubjectView> getStudentGradesBySubject(@PathVariable SubjectId subjectId) {
        GetStudentsGradesBySubjectQuery query = new GetStudentsGradesBySubjectQuery(subjectId);

        return dispatcher.dispatch(query);
    }

    @GetMapping("/{subjectId}/students/{studentId}/grades")
    @ResponseStatus(HttpStatus.OK)
    public StudentGradesBySubjectView getGradesOfSpecificStudentBySubject(@PathVariable SubjectId subjectId, @PathVariable StudentId studentId) {
        GetGradesOfSpecificStudentBySubjectQuery query = new GetGradesOfSpecificStudentBySubjectQuery(subjectId, studentId);

        return dispatcher.dispatch(query);
    }

    @GetMapping("/students/{studentId}/grades")
    @ResponseStatus(HttpStatus.OK)
    public StudentAllSubjectsGradesHeaderView getAllSubjectsGradesOfSpecificStudent(@PathVariable StudentId studentId) {
        GetAllSubjectsGradesOfSpecificStudentQuery query = new GetAllSubjectsGradesOfSpecificStudentQuery(studentId);

        return dispatcher.dispatch(query);
    }


}
