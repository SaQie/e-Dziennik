package pl.edziennik.infrastructure.repositories.schoollevel;

import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.valueobject.id.SchoolLevelId;
import pl.edziennik.domain.schoollevel.SchoolLevel;

@RepositoryDefinition(domainClass = SchoolLevel.class, idClass = SchoolLevelId.class)
public interface SchoolLevelQueryRepository {
}
