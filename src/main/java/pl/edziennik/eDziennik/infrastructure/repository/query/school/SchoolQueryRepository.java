package pl.edziennik.eDziennik.infrastructure.repository.query.school;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edziennik.eDziennik.domain.school.domain.School;
import pl.edziennik.eDziennik.domain.school.domain.wrapper.SchoolId;

@Repository
public interface SchoolQueryRepository extends JpaRepository<School, SchoolId> {
}
