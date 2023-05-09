package pl.edziennik.infrastructure.repositories.schoollevel;

import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.domain.schoollevel.SchoolLevel;
import pl.edziennik.domain.schoollevel.SchoolLevelId;

@RepositoryDefinition(domainClass = SchoolLevel.class, idClass = SchoolLevelId.class)
public interface SchoolLevelQueryRepository {
}
