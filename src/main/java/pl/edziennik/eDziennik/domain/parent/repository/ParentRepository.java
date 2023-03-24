package pl.edziennik.eDziennik.domain.parent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.edziennik.eDziennik.domain.parent.domain.Parent;

@Repository
public interface ParentRepository extends JpaRepository<Parent, Long> {

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN TRUE ELSE FALSE END FROM Parent p " +
            "JOIN p.personInformation pi " +
            "JOIN p.user u " +
            "WHERE pi.pesel = :pesel " +
            "AND u.role.id = :idRole")
    boolean existsByPesel(String pesel, Long idRole);

}
