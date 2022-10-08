package pl.edziennik.eDziennik.server.school;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edziennik.eDziennik.server.school.domain.School;

@Repository
public interface SchoolRepository extends JpaRepository<School, Long> {



}
