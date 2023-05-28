package pl.edziennik.web.query.subject;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edziennik.application.common.dispatcher.Dispatcher;
import pl.edziennik.application.query.subject.detailed.GetDetailedSubjectQuery;
import pl.edziennik.application.query.subject.specificstudentgrades.GetGradesOfSpecificStudentBySubjectQuery;
import pl.edziennik.application.query.subject.studentsgrades.all.GetAllSubjectsGradesOfSpecificStudentQuery;
import pl.edziennik.application.query.subject.studentsgrades.bysubject.GetStudentsGradesBySubjectQuery;
import pl.edziennik.common.dto.grade.allsubjects.StudentAllSubjectsGradesHeaderDto;
import pl.edziennik.common.dto.grade.bysubject.StudentGradesBySubjectDto;
import pl.edziennik.common.dto.subject.DetailedSubjectDto;
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
    public ResponseEntity<DetailedSubjectDto> getSubject(@PathVariable SubjectId subjectId) {
        GetDetailedSubjectQuery query = new GetDetailedSubjectQuery(subjectId);

        DetailedSubjectDto dto = dispatcher.dispatch(query);

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{subjectId}/students/grades")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<StudentGradesBySubjectDto>> getStudentGradesBySubject(@PathVariable SubjectId subjectId) {
        GetStudentsGradesBySubjectQuery query = new GetStudentsGradesBySubjectQuery(subjectId);

        List<StudentGradesBySubjectDto> dto = dispatcher.dispatch(query);

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{subjectId}/students/{studentId}/grades")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<StudentGradesBySubjectDto> getGradesOfSpecificStudentBySubject(@PathVariable SubjectId subjectId, @PathVariable StudentId studentId) {
        GetGradesOfSpecificStudentBySubjectQuery query = new GetGradesOfSpecificStudentBySubjectQuery(subjectId, studentId);

        StudentGradesBySubjectDto dto = dispatcher.dispatch(query);

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/students/{studentId}/grades")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<StudentAllSubjectsGradesHeaderDto> getAllSubjectsGradesOfSpecificStudent(@PathVariable StudentId studentId) {
        GetAllSubjectsGradesOfSpecificStudentQuery query = new GetAllSubjectsGradesOfSpecificStudentQuery(studentId);

        StudentAllSubjectsGradesHeaderDto dto = dispatcher.dispatch(query);

        return ResponseEntity.ok(dto);
    }


}
