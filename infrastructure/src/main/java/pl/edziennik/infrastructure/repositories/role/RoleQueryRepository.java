package pl.edziennik.infrastructure.repositories.role;

import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.domain.role.Role;
import pl.edziennik.domain.role.RoleId;

@RepositoryDefinition(domainClass = Role.class, idClass = RoleId.class)
public interface RoleQueryRepository {

}
