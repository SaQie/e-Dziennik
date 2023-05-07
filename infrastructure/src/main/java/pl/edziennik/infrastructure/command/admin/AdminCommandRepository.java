package pl.edziennik.infrastructure.command.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edziennik.domain.admin.Admin;
import pl.edziennik.domain.admin.AdminId;

@Repository
public interface AdminCommandRepository extends JpaRepository<Admin, AdminId> {
}
