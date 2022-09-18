package pl.edziennik.eDziennik.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edziennik.eDziennik.entities.RatingSubjectStudentLink;

@Repository
public interface RatingSubjectStudentLinkRepository extends JpaRepository<RatingSubjectStudentLink, Long> {
}
