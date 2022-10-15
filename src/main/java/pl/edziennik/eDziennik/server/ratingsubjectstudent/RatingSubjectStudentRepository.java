package pl.edziennik.eDziennik.server.ratingsubjectstudent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edziennik.eDziennik.server.ratingsubjectstudent.domain.RatingSubjectStudentLink;

import java.util.List;

@Repository
public interface RatingSubjectStudentRepository extends JpaRepository<RatingSubjectStudentLink, Long> {



}
