package pl.edziennik.infrastructure.repositories.admin;

import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.valueobject.id.AdminId;
import pl.edziennik.domain.admin.Admin;

@RepositoryDefinition(domainClass = Admin.class, idClass = AdminId.class)
public interface AdminQueryRepository{
}
