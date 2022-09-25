package pl.edziennik.eDziennik.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edziennik.eDziennik.entities.SchoolClass;
import pl.edziennik.eDziennik.entities.SchoolLevel;

@Repository
public interface SchoolLevelRepository extends JpaRepository<SchoolLevel, Long> {
}
