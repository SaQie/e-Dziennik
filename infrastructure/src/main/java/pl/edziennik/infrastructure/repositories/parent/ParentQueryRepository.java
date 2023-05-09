package pl.edziennik.infrastructure.repositories.parent;

import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.domain.parent.Parent;
import pl.edziennik.domain.parent.ParentId;

@RepositoryDefinition(domainClass = Parent.class, idClass = ParentId.class)
public interface ParentQueryRepository {
}
