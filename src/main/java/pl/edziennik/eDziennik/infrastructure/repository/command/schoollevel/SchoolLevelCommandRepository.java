package pl.edziennik.eDziennik.infrastructure.repository.command.schoollevel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edziennik.eDziennik.domain.schoollevel.domain.SchoolLevel;
import pl.edziennik.eDziennik.domain.schoollevel.domain.wrapper.SchoolLevelId;

@Repository
public interface SchoolLevelCommandRepository extends JpaRepository<SchoolLevel, SchoolLevelId> {
}
