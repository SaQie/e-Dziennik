package pl.edziennik.eDziennik.domain.role.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edziennik.eDziennik.domain.role.domain.Role;
import pl.edziennik.eDziennik.domain.role.domain.wrapper.RoleId;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, RoleId> {

    Optional<Role> findByName(String name);

}
