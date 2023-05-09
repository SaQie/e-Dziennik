package pl.edziennik.infrastructure.repositories.admin;

import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.domain.admin.Admin;
import pl.edziennik.domain.admin.AdminId;

@RepositoryDefinition(domainClass = Admin.class, idClass = AdminId.class)
public interface AdminQueryRepository{
}
