package pl.edziennik.eDziennik.infrastructure.repository.command.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edziennik.eDziennik.domain.admin.domain.Admin;
import pl.edziennik.eDziennik.domain.admin.domain.wrapper.AdminId;

@Repository
public interface AdminCommandRepository extends JpaRepository<Admin, AdminId> {
}
