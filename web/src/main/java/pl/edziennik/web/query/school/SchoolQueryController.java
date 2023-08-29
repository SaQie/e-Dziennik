package pl.edziennik.web.query.school;

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
public class SchoolQueryController {

    private final SchoolQueryDao dao;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PageView<SchoolSummaryView> getSchoolList(Pageable pageable) {
        return dao.getSchoolSummaryView(pageable);
    }

    @GetMapping("/{schoolId}")
    @ResponseStatus(HttpStatus.OK)
    public DetailedSchoolView getSchool(@PathVariable SchoolId schoolId) {
        return dao.getDetailedSchoolView(schoolId);
    }

    @GetMapping("/{schoolId}/configurations")
    @ResponseStatus(HttpStatus.OK)
    public SchoolConfigSummaryView getSchoolConfigurationValues(@PathVariable SchoolId schoolId) {
        return dao.getSchoolConfigSummaryView(schoolId);
    }


}
