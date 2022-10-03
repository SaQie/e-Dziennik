package pl.edziennik.eDziennik.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edziennik.eDziennik.server.rating.RatingSubjectStudentLink;

@Repository
public interface RatingSubjectStudentLinkRepository extends JpaRepository<RatingSubjectStudentLink, Long> {
}
