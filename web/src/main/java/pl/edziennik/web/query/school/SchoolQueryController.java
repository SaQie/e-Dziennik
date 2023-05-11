package pl.edziennik.web.query.school;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edziennik.application.common.dispatcher.Dispatcher;
import pl.edziennik.application.query.school.detailedschool.GetDetailedSchoolQuery;
import pl.edziennik.application.query.school.schoolsummary.GetSchoolSummaryQuery;
import pl.edziennik.common.dto.PageDto;
import pl.edziennik.common.dto.school.DetailedSchoolDto;
import pl.edziennik.common.dto.school.SchoolSummaryDto;
import pl.edziennik.common.valueobject.id.SchoolId;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/schools")
public class SchoolQueryController {

    private final Dispatcher dispatcher;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PageDto<SchoolSummaryDto>> getSchoolList(Pageable pageable) {
        GetSchoolSummaryQuery getSchoolSummaryQuery = new GetSchoolSummaryQuery(pageable);

        PageDto<SchoolSummaryDto> schoolSummaryDtoPageDto = dispatcher.callHandler(getSchoolSummaryQuery);

        return ResponseEntity.ok(schoolSummaryDtoPageDto);
    }

    @GetMapping("/{schoolId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<DetailedSchoolDto> getSchool(@PathVariable SchoolId schoolId) {
        GetDetailedSchoolQuery getDetailedSchoolQuery = new GetDetailedSchoolQuery(schoolId);

        DetailedSchoolDto detailedSchoolDto = dispatcher.callHandler(getDetailedSchoolQuery);

        return ResponseEntity.ok(detailedSchoolDto);
    }


}
