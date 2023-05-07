package pl.edziennik.infrastructure.command.role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edziennik.domain.role.Role;
import pl.edziennik.domain.role.RoleId;

@Repository
public interface RoleCommandRepository extends JpaRepository<Role, RoleId> {
}
