package pl.edziennik.eDziennik.server.schoollevel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edziennik.eDziennik.server.schoollevel.domain.SchoolLevel;

@Repository
public interface SchoolLevelRepository extends JpaRepository<SchoolLevel, Long> {
}
