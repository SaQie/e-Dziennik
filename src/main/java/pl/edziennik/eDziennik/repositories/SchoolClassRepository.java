package pl.edziennik.eDziennik.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edziennik.eDziennik.entities.SchoolClass;

@Repository
public interface SchoolClassRepository extends JpaRepository<SchoolClass, Long> {
}