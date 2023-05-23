package pl.edziennik.infrastructure.repository.schoollevel;

import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.valueobject.id.SchoolLevelId;
import pl.edziennik.domain.schoollevel.SchoolLevel;

import java.util.Optional;

@RepositoryDefinition(domainClass = SchoolLevel.class, idClass = SchoolLevelId.class)
public interface SchoolLevelCommandRepository {
    Optional<SchoolLevel> findById(SchoolLevelId schoolLevelId);

}