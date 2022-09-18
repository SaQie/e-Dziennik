package pl.edziennik.eDziennik.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edziennik.eDziennik.entities.Rating;

@Repository
public interface RatingRepository extends JpaRepository<Rating,Long> {
}
