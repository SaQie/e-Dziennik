package pl.edziennik.eDziennik.infrastructure.repository.command.schoolclass;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edziennik.eDziennik.domain.schoolclass.domain.SchoolClass;
import pl.edziennik.eDziennik.domain.schoolclass.domain.wrapper.SchoolClassId;

@Repository
public interface SchoolClassCommandRepository extends JpaRepository<SchoolClass, SchoolClassId> {
}