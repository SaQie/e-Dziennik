package pl.edziennik.eDziennik.domain.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edziennik.eDziennik.domain.school.domain.School;

@Repository
public interface SchoolRepository extends JpaRepository<School, Long> {

    boolean existsByName(String name);

    boolean existsByNip(String nip);

    boolean existsByRegon(String regon);

}
