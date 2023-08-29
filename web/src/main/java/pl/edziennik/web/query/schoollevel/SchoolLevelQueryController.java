package pl.edziennik.web.query.schoollevel;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edziennik.application.query.schoollevel.SchoolLevelQueryDao;
import pl.edziennik.common.view.schoollevel.SchoolLevelView;

import java.util.List;

@AllArgsConstructor
@RequestMapping("/api/v1/schoollevels")
@RestController
public class SchoolLevelQueryController {

    private final SchoolLevelQueryDao dao;

    @GetMapping()
    public List<SchoolLevelView> getSchoolLevels() {
        return dao.getSchoolLevelsView();
    }

}
