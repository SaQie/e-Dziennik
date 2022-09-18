package pl.edziennik.eDziennik.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edziennik.eDziennik.entities.School;

@Repository
public interface SchoolRepository extends JpaRepository<School, Long> {
}
