package pl.edziennik.web.query.student;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<DetailedStudentDto> getStudentById(@PathVariable StudentId studentId) {
        GetDetailedStudentQuery getDetailedStudentQuery = new GetDetailedStudentQuery(studentId);

        DetailedStudentDto detailedStudentDto = dispatcher.callHandler(getDetailedStudentQuery);

        return ResponseEntity.ok(detailedStudentDto);

    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PageDto<StudentSummaryDto>> getAllStudents(Pageable pageable) {
        GetStudentSummaryQuery getStudentSummaryQuery = new GetStudentSummaryQuery(pageable);

        PageDto<StudentSummaryDto> studentDtoPageDto = dispatcher.callHandler(getStudentSummaryQuery);

        return ResponseEntity.ok(studentDtoPageDto);
    }

    // FIXME CQRS DOROBIC ENDPOINTY

    // /api/v1/students/{studentId}/subjects/grades <- Student subject list with grades
    // /api/b1/students/{studentId}/subjects/{subjectId}/grades <- Student specific subject with grades


}
