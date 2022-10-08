package pl.edziennik.eDziennik.server.rating;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edziennik.eDziennik.server.rating.domain.Rating;

@Repository
public interface RatingRepository extends JpaRepository<Rating,Long> {
}
