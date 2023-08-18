package pl.edziennik.web.query.schoolclass;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.edziennik.application.common.dispatcher.Dispatcher;
import pl.edziennik.application.query.schoolclass.config.GetSchoolClassConfigSummaryQuery;
import pl.edziennik.application.query.schoolclass.detailed.GetDetailedSchoolClassQuery;
import pl.edziennik.application.query.schoolclass.summary.GetSchoolClassSummaryForSchoolQuery;
import pl.edziennik.common.view.PageView;
import pl.edziennik.common.view.schoolclass.DetailedSchoolClassView;
import pl.edziennik.common.view.schoolclass.SchoolClassSummaryForSchoolView;
import pl.edziennik.common.view.schoolclass.config.SchoolClassConfigSummaryView;
import pl.edziennik.common.valueobject.id.SchoolClassId;
import pl.edziennik.common.valueobject.id.SchoolId;

@RestController
@RequestMapping("/api/v1/schoolclasses")
@AllArgsConstructor
public class SchoolClassQueryController {

    private final Dispatcher dispatcher;

    @GetMapping("/{schoolClassId}")
    @ResponseStatus(HttpStatus.OK)
    public DetailedSchoolClassView getDetailedSchoolClass(@PathVariable SchoolClassId schoolClassId) {
        GetDetailedSchoolClassQuery schoolClassQuery = new GetDetailedSchoolClassQuery(schoolClassId);

        return dispatcher.dispatch(schoolClassQuery);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PageView<SchoolClassSummaryForSchoolView> getSchoolClassSummaryForSchool(@RequestParam SchoolId schoolId, Pageable pageable) {
        GetSchoolClassSummaryForSchoolQuery query = new GetSchoolClassSummaryForSchoolQuery(schoolId, pageable);

        return dispatcher.dispatch(query);
    }

    @GetMapping("/{schoolClassId}/configurations")
    @ResponseStatus(HttpStatus.OK)
    public SchoolClassConfigSummaryView getSchoolClassConfiguration(@PathVariable SchoolClassId schoolClassId) {
        GetSchoolClassConfigSummaryQuery query = new GetSchoolClassConfigSummaryQuery(schoolClassId);

        return dispatcher.dispatch(query);
    }

}
