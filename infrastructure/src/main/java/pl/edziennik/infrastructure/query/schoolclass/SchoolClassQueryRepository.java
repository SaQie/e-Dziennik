package pl.edziennik.infrastructure.query.schoolclass;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edziennik.domain.schoolclass.SchoolClass;
import pl.edziennik.domain.schoolclass.SchoolClassId;

@Repository
public interface SchoolClassQueryRepository extends JpaRepository<SchoolClass, SchoolClassId> {




}
