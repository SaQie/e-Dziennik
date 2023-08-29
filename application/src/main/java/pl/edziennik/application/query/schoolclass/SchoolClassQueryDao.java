package pl.edziennik.application.query.schoolclass;

import org.springframework.data.domain.Pageable;
import pl.edziennik.common.valueobject.id.SchoolClassId;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.common.view.PageView;
import pl.edziennik.common.view.schoolclass.DetailedSchoolClassView;
import pl.edziennik.common.view.schoolclass.SchoolClassSummaryForSchoolView;
import pl.edziennik.common.view.schoolclass.config.SchoolClassConfigSummaryView;

public interface SchoolClassQueryDao {

    PageView<SchoolClassSummaryForSchoolView> getSchoolClassSummaryForSchoolView(SchoolId schoolId, Pageable pageable);

    DetailedSchoolClassView getDetailedSchoolClassView(SchoolClassId schoolClassId);

    SchoolClassConfigSummaryView getSchoolClassConfigSummaryView(SchoolClassId schoolClassId);

}
