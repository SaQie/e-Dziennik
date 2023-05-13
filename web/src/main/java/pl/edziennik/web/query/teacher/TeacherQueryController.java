package pl.edziennik.web.query.teacher;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edziennik.application.common.dispatcher.Dispatcher;
import pl.edziennik.application.query.teacher.detailed.GetDetailedTeacherQuery;
import pl.edziennik.application.query.teacher.subjects.GetTeacherSubjectsSummaryQuery;
import pl.edziennik.application.query.teacher.summary.GetTeacherSummaryQuery;
import pl.edziennik.common.dto.PageDto;
import pl.edziennik.common.dto.teacher.DetailedTeacherDto;
import pl.edziennik.common.dto.teacher.TeacherDetailedSubjectDto;
import pl.edziennik.common.dto.teacher.TeacherSubjectsSummaryDto;
import pl.edziennik.common.dto.teacher.TeacherSummaryDto;
import pl.edziennik.common.valueobject.id.SubjectId;
import pl.edziennik.common.valueobject.id.TeacherId;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/teachers")
public class TeacherQueryController {

    private final Dispatcher dispatcher;

    @GetMapping("/{teacherId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<DetailedTeacherDto> getTeacherById(@PathVariable TeacherId teacherId) {
        GetDetailedTeacherQuery query = new GetDetailedTeacherQuery(teacherId);

        DetailedTeacherDto dto = dispatcher.callHandler(query);

        return ResponseEntity.ok(dto);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PageDto<TeacherSummaryDto>> getAllTeachers(Pageable pageable) {
        GetTeacherSummaryQuery query = new GetTeacherSummaryQuery(pageable);

        PageDto<TeacherSummaryDto> dto = dispatcher.callHandler(query);

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{teacherId}/subjects")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PageDto<TeacherSubjectsSummaryDto>> getTeacherSubjectsSummary(@PathVariable TeacherId teacherId, Pageable pageable) {
        GetTeacherSubjectsSummaryQuery query = new GetTeacherSubjectsSummaryQuery(pageable, teacherId);

        PageDto<TeacherSubjectsSummaryDto> dto = dispatcher.callHandler(query);

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{teacherId}/subjects/{subjectId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<TeacherDetailedSubjectDto> getDetailedTeacherSubject(@PathVariable TeacherId teacherId, @PathVariable SubjectId subjectId) {
        // dokonczyc ten query
        return null;
    }


}
