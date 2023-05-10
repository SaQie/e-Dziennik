package pl.edziennik.infrastructure.repositories.school;

import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.valueobject.id.SchoolId;
import pl.edziennik.domain.school.School;

@RepositoryDefinition(domainClass = School.class, idClass = SchoolId.class)
public interface SchoolQueryRepository {
}
