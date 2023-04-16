package pl.edziennik.eDziennik.domain.school.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edziennik.eDziennik.domain.school.domain.School;
import pl.edziennik.eDziennik.domain.school.domain.wrapper.SchoolId;

@Repository
public interface SchoolRepository extends JpaRepository<School, SchoolId> {

    boolean existsByName(String name);

    boolean existsByNip(String nip);

    boolean existsByRegon(String regon);

    @EntityGraph(
            type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {
                    "address",
                    "schoolClasses",
            }
    )
    Page<School> findAll(Pageable pageable);

}
