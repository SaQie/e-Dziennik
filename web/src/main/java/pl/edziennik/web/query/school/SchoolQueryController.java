package pl.edziennik.web.query.school;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.edziennik.application.common.dispatcher.Dispatcher;
import pl.edziennik.application.query.school.config.GetSchoolConfigSummaryQuery;
import pl.edziennik.application.query.school.detailed.GetDetailedSchoolQuery;
import pl.edziennik.application.query.school.summary.GetSchoolSummaryQuery;
import pl.edziennik.common.dto.PageDto;
import pl.edziennik.common.dto.school.DetailedSchoolDto;
import pl.edziennik.common.dto.school.SchoolSummaryDto;
import pl.edziennik.common.dto.school.config.SchoolConfigSummaryDto;
import pl.edziennik.common.valueobject.id.SchoolId;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/schools")
public class SchoolQueryController {

    private final Dispatcher dispatcher;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Cacheable(value = "schools", key = "#root.method.name")
    public PageDto<SchoolSummaryDto> getSchoolList(Pageable pageable) {
        GetSchoolSummaryQuery getSchoolSummaryQuery = new GetSchoolSummaryQuery(pageable);

        return dispatcher.dispatch(getSchoolSummaryQuery);
    }

    @GetMapping("/{schoolId}")
    @ResponseStatus(HttpStatus.OK)
    public DetailedSchoolDto getSchool(@PathVariable SchoolId schoolId) {
        GetDetailedSchoolQuery getDetailedSchoolQuery = new GetDetailedSchoolQuery(schoolId);

        return dispatcher.dispatch(getDetailedSchoolQuery);
    }

    @GetMapping("/{schoolId}/configurations")
    @ResponseStatus(HttpStatus.OK)
    public SchoolConfigSummaryDto getSchoolConfigurationValues(@PathVariable SchoolId schoolId) {
        GetSchoolConfigSummaryQuery query = new GetSchoolConfigSummaryQuery(schoolId);

        return dispatcher.dispatch(query);
    }


}
