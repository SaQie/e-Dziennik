package pl.edziennik.web.query.schoolclass;

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
public class SchoolClassQueryController {

    private final SchoolClassQueryDao dao;

    @GetMapping("/schoolclasses/{schoolClassId}")
    @ResponseStatus(HttpStatus.OK)
    public DetailedSchoolClassView getDetailedSchoolClass(@PathVariable SchoolClassId schoolClassId) {
        return dao.getDetailedSchoolClassView(schoolClassId);
    }

    @GetMapping("/schools/{schoolId}/schoolclasses")
    @ResponseStatus(HttpStatus.OK)
    public PageView<SchoolClassSummaryForSchoolView> getSchoolClassSummaryForSchool(@PathVariable SchoolId schoolId, Pageable pageable) {
        return dao.getSchoolClassSummaryForSchoolView(schoolId, pageable);
    }

    @GetMapping("/schoolclasses/{schoolClassId}/configurations")
    @ResponseStatus(HttpStatus.OK)
    public SchoolClassConfigSummaryView getSchoolClassConfiguration(@PathVariable SchoolClassId schoolClassId) {
        return dao.getSchoolClassConfigSummaryView(schoolClassId);
    }

}
