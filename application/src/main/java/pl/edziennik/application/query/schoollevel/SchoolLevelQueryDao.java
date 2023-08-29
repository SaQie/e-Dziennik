package pl.edziennik.application.query.schoollevel;

import pl.edziennik.common.view.schoollevel.SchoolLevelView;

import java.util.List;

public interface SchoolLevelQueryDao {

    List<SchoolLevelView> getSchoolLevelsView();

}
