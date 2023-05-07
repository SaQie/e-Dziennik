package pl.edziennik.infrastructure.query.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edziennik.domain.admin.Admin;
import pl.edziennik.domain.admin.AdminId;

@Repository
public interface AdminQueryRepository extends JpaRepository<Admin, AdminId> {
}
