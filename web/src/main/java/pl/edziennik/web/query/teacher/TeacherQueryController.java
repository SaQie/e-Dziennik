package pl.edziennik.web.query.teacher;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edziennik.application.common.dispatcher.Dispatcher;
import pl.edziennik.application.query.teacher.detailedteacher.GetDetailedTeacherQuery;
import pl.edziennik.application.query.teacher.teachersummary.GetTeacherSummaryQuery;
import pl.edziennik.common.dto.PageDto;
import pl.edziennik.common.dto.teacher.DetailedTeacherDto;
import pl.edziennik.common.dto.teacher.TeacherSummaryDto;
import pl.edziennik.common.valueobject.id.TeacherId;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/teachers")
public class TeacherQueryController {

    private final Dispatcher dispatcher;

    @GetMapping("/{teacherId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<DetailedTeacherDto> getTeacherById(@PathVariable TeacherId teacherId) {
        GetDetailedTeacherQuery getDetailedTeacherQuery = new GetDetailedTeacherQuery(teacherId);

        DetailedTeacherDto dto = dispatcher.callHandler(getDetailedTeacherQuery);

        return ResponseEntity.ok(dto);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PageDto<TeacherSummaryDto>> getAllTeachers(Pageable pageable) {
        GetTeacherSummaryQuery getTeacherSummaryQuery = new GetTeacherSummaryQuery(pageable);

        PageDto<TeacherSummaryDto> dto = dispatcher.callHandler(getTeacherSummaryQuery);

        return ResponseEntity.ok(dto);
    }

}
