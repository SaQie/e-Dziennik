package pl.edziennik.application.query.school;

import org.springframework.data.domain.Pageable;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.view.PageView;
import pl.edziennik.common.view.school.DetailedSchoolView;
import pl.edziennik.common.view.school.SchoolSummaryView;
import pl.edziennik.common.view.school.config.SchoolConfigSummaryView;

public interface SchoolQueryDao {

    SchoolConfigSummaryView getSchoolConfigSummaryView(SchoolId schoolId);

    PageView<SchoolSummaryView> getSchoolSummaryView(Pageable pageable);

    DetailedSchoolView getDetailedSchoolView(SchoolId schoolId);
}
