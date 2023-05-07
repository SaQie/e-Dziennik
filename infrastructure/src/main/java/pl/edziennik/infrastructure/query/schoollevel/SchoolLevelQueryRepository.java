package pl.edziennik.infrastructure.query.schoollevel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edziennik.domain.schoollevel.SchoolLevel;
import pl.edziennik.domain.schoollevel.SchoolLevelId;

@Repository
public interface SchoolLevelQueryRepository extends JpaRepository<SchoolLevel, SchoolLevelId> {
}
