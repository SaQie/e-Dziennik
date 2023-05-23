package pl.edziennik.infrastructure.repository.schoollevel;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.valueobject.Name;
import pl.edziennik.common.valueobject.id.SchoolLevelId;
import pl.edziennik.domain.schoollevel.SchoolLevel;

@RepositoryDefinition(domainClass = SchoolLevel.class, idClass = SchoolLevelId.class)
public interface SchoolLevelQueryRepository {

    @Query("SELECT sl.schoolLevelId FROM SchoolLevel sl where sl.name = :name")
    SchoolLevelId getByName(Name name);

}
