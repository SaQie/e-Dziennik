package pl.edziennik.infrastructure.query.school;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edziennik.domain.school.School;
import pl.edziennik.domain.school.SchoolId;

@Repository
public interface SchoolQueryRepository extends JpaRepository<School, SchoolId> {
}
