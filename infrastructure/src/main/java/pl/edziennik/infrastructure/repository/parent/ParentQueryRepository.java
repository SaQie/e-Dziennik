package pl.edziennik.infrastructure.repository.parent;

import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.valueobject.id.ParentId;
import pl.edziennik.domain.parent.Parent;

@RepositoryDefinition(domainClass = Parent.class, idClass = ParentId.class)
public interface ParentQueryRepository {
}
