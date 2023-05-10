package pl.edziennik.infrastructure.repositories.role;

import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.valueobject.id.RoleId;
import pl.edziennik.domain.role.Role;

@RepositoryDefinition(domainClass = Role.class, idClass = RoleId.class)
public interface RoleQueryRepository {

}
