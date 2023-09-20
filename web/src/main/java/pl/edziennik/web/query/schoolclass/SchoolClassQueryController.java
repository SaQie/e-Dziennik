package pl.edziennik.web.query.schoolclass;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.edziennik.application.query.schoolclass.SchoolClassQueryDao;
import pl.edziennik.common.valueobject.id.SchoolClassId;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.view.PageView;
import pl.edziennik.common.view.schoolclass.DetailedSchoolClassView;
import pl.edziennik.common.view.schoolclass.SchoolClassSummaryForSchoolView;
import pl.edziennik.common.view.schoolclass.config.SchoolClassConfigSummaryView;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
@Tag(name = "School-class API")
public class SchoolClassQueryController {

    private final SchoolClassQueryDao dao;

    @Operation(summary = "Get detailed information about given school-class")
    @GetMapping("/school-classes/{schoolClassId}")
    @ResponseStatus(HttpStatus.OK)
    public DetailedSchoolClassView getDetailedSchoolClass(@PathVariable SchoolClassId schoolClassId) {
        return dao.getDetailedSchoolClassView(schoolClassId);
    }

    @Operation(summary = "Get school-classes assigned to given school")
    @GetMapping("/schools/{schoolId}/school-classes")
    @ResponseStatus(HttpStatus.OK)
    public PageView<SchoolClassSummaryForSchoolView> getSchoolClassSummaryForSchool(@PathVariable SchoolId schoolId, Pageable pageable) {
        return dao.getSchoolClassSummaryForSchoolView(schoolId, pageable);
    }

    @Operation(summary = "Get given school-class configuration parameters data")
    @GetMapping("/school-classes/{schoolClassId}/configurations")
    @ResponseStatus(HttpStatus.OK)
    public SchoolClassConfigSummaryView getSchoolClassConfiguration(@PathVariable SchoolClassId schoolClassId) {
        return dao.getSchoolClassConfigSummaryView(schoolClassId);
    }

}
