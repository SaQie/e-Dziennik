package pl.edziennik.eDziennik.domain.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.edziennik.eDziennik.domain.admin.domain.Admin;
import pl.edziennik.eDziennik.domain.admin.domain.wrapper.AdminId;

@Repository
public interface AdminRepository extends JpaRepository<Admin, AdminId> {

    @Query("select a from Admin a where a.user.username = :username")
    Admin getAdminByUsername(String username);

}
