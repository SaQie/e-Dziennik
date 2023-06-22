package pl.edziennik.web.query.teacher;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.edziennik.application.common.dispatcher.Dispatcher;
import pl.edziennik.application.query.teacher.detailed.GetDetailedTeacherQuery;
import pl.edziennik.application.query.teacher.subjects.GetTeacherSubjectsSummaryQuery;
import pl.edziennik.application.query.teacher.summary.GetTeacherSummaryQuery;
import pl.edziennik.common.dto.PageDto;
import pl.edziennik.common.dto.teacher.DetailedTeacherDto;
import pl.edziennik.common.dto.teacher.TeacherSubjectsSummaryDto;
import pl.edziennik.common.dto.teacher.TeacherSummaryDto;
import pl.edziennik.common.valueobject.id.TeacherId;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/teachers")
public class TeacherQueryController {

    private final Dispatcher dispatcher;

    @GetMapping("/{teacherId}")
    @ResponseStatus(HttpStatus.OK)
    public DetailedTeacherDto getTeacherById(@PathVariable TeacherId teacherId) {
        GetDetailedTeacherQuery query = new GetDetailedTeacherQuery(teacherId);

        return dispatcher.dispatch(query);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    @Cacheable(value = "teachers", key = "#root.method.name")
    public PageDto<TeacherSummaryDto> getAllTeachers(Pageable pageable) {
        GetTeacherSummaryQuery query = new GetTeacherSummaryQuery(pageable);

        return dispatcher.dispatch(query);
    }

    @GetMapping("/{teacherId}/subjects")
    @ResponseStatus(HttpStatus.OK)
    public PageDto<TeacherSubjectsSummaryDto> getTeacherSubjectsSummary(@PathVariable TeacherId teacherId, Pageable pageable) {
        GetTeacherSubjectsSummaryQuery query = new GetTeacherSubjectsSummaryQuery(pageable, teacherId);

        return dispatcher.dispatch(query);
    }


}
