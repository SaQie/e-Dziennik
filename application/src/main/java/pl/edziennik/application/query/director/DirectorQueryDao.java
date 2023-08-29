package pl.edziennik.application.query.director;

import pl.edziennik.common.valueobject.id.DirectorId;
import pl.edziennik.common.view.director.DetailedDirectorView;

public interface DirectorQueryDao {

    DetailedDirectorView getDetailedDirectorView(DirectorId directorId);

}
