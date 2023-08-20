package pl.edziennik.infrastructure.repository.schoollevel;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.view.schoollevel.SchoolLevelView;
import pl.edziennik.common.valueobject.vo.Name;
import pl.edziennik.common.valueobject.id.SchoolLevelId;
import pl.edziennik.domain.schoollevel.SchoolLevel;

import java.util.List;

@RepositoryDefinition(domainClass = SchoolLevel.class, idClass = SchoolLevelId.class)
public interface SchoolLevelQueryRepository {

    @Query("SELECT sl.schoolLevelId FROM SchoolLevel sl where sl.name = :name")
    SchoolLevelId getByName(Name name);

    @Query("SELECT NEW pl.edziennik.common.view.schoollevel.SchoolLevelView(sl.schoolLevelId, sl.name) " +
            "FROM SchoolLevel sl ")
    List<SchoolLevelView> findAll();

}
