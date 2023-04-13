package pl.edziennik.eDziennik.domain.parent.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.edziennik.eDziennik.domain.parent.domain.Parent;
import pl.edziennik.eDziennik.domain.personinformation.domain.wrapper.Pesel;

@Repository
public interface ParentRepository extends JpaRepository<Parent, Long> {

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN TRUE ELSE FALSE END FROM Parent p " +
            "JOIN p.personInformation pi " +
            "JOIN p.user u " +
            "WHERE pi.pesel = :pesel " +
            "AND u.role.id = :idRole")
    boolean existsByPesel(Pesel pesel, Long idRole);

    @EntityGraph(
            type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {
                    "student",
                    "user",
                    "personInformation",
                    "address"
            }
    )
    Page<Parent> findAll(Pageable pageable);

}
