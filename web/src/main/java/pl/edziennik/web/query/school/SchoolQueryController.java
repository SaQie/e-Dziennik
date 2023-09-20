package pl.edziennik.web.query.school;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.edziennik.application.query.school.SchoolQueryDao;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.view.PageView;
import pl.edziennik.common.view.school.DetailedSchoolView;
import pl.edziennik.common.view.school.SchoolSummaryView;
import pl.edziennik.common.view.school.config.SchoolConfigSummaryView;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/schools")
@Tag(name = "School API")
public class SchoolQueryController {

    private final SchoolQueryDao dao;

    @Operation(summary = "Get list of all schools")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PageView<SchoolSummaryView> getSchoolList(Pageable pageable) {
        return dao.getSchoolSummaryView(pageable);
    }

    @Operation(summary = "Get detailed information about given school")
    @GetMapping("/{schoolId}")
    @ResponseStatus(HttpStatus.OK)
    public DetailedSchoolView getSchool(@PathVariable SchoolId schoolId) {
        return dao.getDetailedSchoolView(schoolId);
    }

    @Operation(summary = "Get given school configuration parameters data")
    @GetMapping("/{schoolId}/configurations")
    @ResponseStatus(HttpStatus.OK)
    public SchoolConfigSummaryView getSchoolConfigurationValues(@PathVariable SchoolId schoolId) {
        return dao.getSchoolConfigSummaryView(schoolId);
    }


}
