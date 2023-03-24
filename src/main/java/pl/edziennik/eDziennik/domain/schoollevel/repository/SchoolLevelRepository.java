package pl.edziennik.eDziennik.domain.schoollevel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edziennik.eDziennik.domain.schoollevel.domain.SchoolLevel;

@Repository
public interface SchoolLevelRepository extends JpaRepository<SchoolLevel, Long> {
}
