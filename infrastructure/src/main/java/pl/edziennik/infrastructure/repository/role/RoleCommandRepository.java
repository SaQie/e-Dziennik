package pl.edziennik.infrastructure.repository.role;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.valueobject.Name;
import pl.edziennik.common.valueobject.id.RoleId;
import pl.edziennik.domain.role.Role;

@RepositoryDefinition(domainClass = Role.class, idClass = RoleId.class)
public interface RoleCommandRepository{

    @Query("SELECT r FROM Role r where r.name = :name")
    Role getByName(Name name);

    Role getByRoleId(RoleId roleId);

}
