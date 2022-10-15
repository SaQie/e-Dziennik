package pl.edziennik.eDziennik.server.rating;

import pl.edziennik.eDziennik.server.rating.domain.dto.RatingRequestApiDto;
import pl.edziennik.eDziennik.server.rating.domain.dto.RatingResponseApiDto;

import java.util.List;

public interface RatingService {


    RatingResponseApiDto addNewRating(final RatingRequestApiDto dto);

    RatingResponseApiDto findRatingById(final Long id);

    void deleteRatingById(final Long id);

    List<RatingResponseApiDto> findAllRatings();
}
