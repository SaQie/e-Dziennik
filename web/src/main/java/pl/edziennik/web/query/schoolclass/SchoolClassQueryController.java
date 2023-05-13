package pl.edziennik.web.query.schoolclass;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edziennik.application.common.dispatcher.Dispatcher;
import pl.edziennik.application.query.schoolclass.detailed.GetDetailedSchoolClassQuery;
import pl.edziennik.application.query.schoolclass.summary.GetSchoolClassSummaryForSchoolQuery;
import pl.edziennik.common.dto.PageDto;
import pl.edziennik.common.dto.schoolclass.DetailedSchoolClassDto;
import pl.edziennik.common.dto.schoolclass.SchoolClassSummaryForSchoolDto;
import pl.edziennik.common.valueobject.id.SchoolClassId;
import pl.edziennik.common.valueobject.id.SchoolId;

@RestController
@RequestMapping("/api/v1/schoolclasses")
@AllArgsConstructor
public class SchoolClassQueryController {

    private final Dispatcher dispatcher;

    @GetMapping("/{schoolClassId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<DetailedSchoolClassDto> getDetailedSchoolClass(@PathVariable SchoolClassId schoolClassId) {
        GetDetailedSchoolClassQuery schoolClassQuery = new GetDetailedSchoolClassQuery(schoolClassId);

        DetailedSchoolClassDto dto = dispatcher.callHandler(schoolClassQuery);

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/schools/{schoolId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PageDto<SchoolClassSummaryForSchoolDto>> getSchoolClassSummaryForSchool(@PathVariable SchoolId schoolId, Pageable pageable) {
        GetSchoolClassSummaryForSchoolQuery query = new GetSchoolClassSummaryForSchoolQuery(schoolId, pageable);

        PageDto<SchoolClassSummaryForSchoolDto> dto = dispatcher.callHandler(query);

        return ResponseEntity.ok(dto);
    }

}
