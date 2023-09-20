package pl.edziennik.web.query.schoollevel;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edziennik.application.query.schoollevel.SchoolLevelQueryDao;
import pl.edziennik.common.view.schoollevel.SchoolLevelView;

import java.util.List;

@AllArgsConstructor
@RequestMapping("/api/v1/school-levels")
@RestController
@Tag(name = "School API")
public class SchoolLevelQueryController {

    private final SchoolLevelQueryDao dao;

    @Operation(summary = "Get available school-levels")
    @GetMapping()
    public List<SchoolLevelView> getSchoolLevels() {
        return dao.getSchoolLevelsView();
    }

}
