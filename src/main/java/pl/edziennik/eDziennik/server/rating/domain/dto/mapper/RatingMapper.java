package pl.edziennik.eDziennik.server.rating.domain.dto.mapper;

import pl.edziennik.eDziennik.server.rating.domain.Rating;
import pl.edziennik.eDziennik.server.rating.domain.dto.RatingRequestApiDto;
import pl.edziennik.eDziennik.server.rating.domain.dto.RatingResponseApiDto;

import java.util.List;
import java.util.stream.Collectors;

public class RatingMapper {

    private RatingMapper() {
    }

    public static RatingResponseApiDto toDto(Rating rating) {
        return new RatingResponseApiDto(
                rating.getId(),
                rating.getRating().getRating(),
                rating.getWeight(),
                rating.getDescription()
        );
    }

    public static List<RatingResponseApiDto> toDto(List<Rating> ratings) {
        return ratings.stream().map(rating -> new RatingResponseApiDto(
                rating.getId(),
                rating.getRating().getRating(),
                rating.getWeight(),
                rating.getDescription()
        )).collect(Collectors.toList());
    }

    public static Rating toEntity(RatingRequestApiDto dto){
        return new Rating(
                Rating.RatingConst.getByRating(dto.getRating()),
                dto.getWeight(),
                dto.getDescription()
        );
    }
}
