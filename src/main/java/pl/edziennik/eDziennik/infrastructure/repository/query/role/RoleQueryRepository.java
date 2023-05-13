package pl.edziennik.eDziennik.infrastructure.repository.query.role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edziennik.eDziennik.domain.role.domain.Role;
import pl.edziennik.eDziennik.domain.role.domain.wrapper.RoleId;

@Repository
public interface RoleQueryRepository extends JpaRepository<Role, RoleId> {
    Role getByName(String role);
}