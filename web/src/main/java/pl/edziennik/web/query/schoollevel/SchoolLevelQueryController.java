package pl.edziennik.web.query.schoollevel;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edziennik.application.common.dispatcher.Dispatcher;
import pl.edziennik.application.query.schoollevel.GetSchoolLevelSummaryQuery;
import pl.edziennik.common.dto.schoollevel.SchoolLevelDto;

import java.util.List;

@AllArgsConstructor
@RequestMapping("/api/v1/schoollevels")
@RestController
public class SchoolLevelQueryController {

    private Dispatcher dispatcher;

    @GetMapping()
    public List<SchoolLevelDto> getSchoolLevels() {
        GetSchoolLevelSummaryQuery query = new GetSchoolLevelSummaryQuery();

        return dispatcher.dispatch(query);
    }

}
