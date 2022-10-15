package pl.edziennik.eDziennik.server.rating.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RatingRequestApiDto {

    private int rating;
    private int weight;
    private String description;

}
