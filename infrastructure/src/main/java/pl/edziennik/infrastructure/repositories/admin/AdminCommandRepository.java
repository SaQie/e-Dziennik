package pl.edziennik.infrastructure.repositories.admin;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.valueobject.id.AdminId;
import pl.edziennik.domain.admin.Admin;

@RepositoryDefinition(domainClass = Admin.class, idClass = AdminId.class)
public interface AdminCommandRepository {

    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN TRUE ELSE FALSE END FROM Admin a")
    boolean isAdminAccountAlreadyExists();

    Admin save(Admin admin);
}
