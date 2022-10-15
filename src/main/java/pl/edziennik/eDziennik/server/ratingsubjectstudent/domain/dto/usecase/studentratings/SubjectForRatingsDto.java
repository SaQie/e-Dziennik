package pl.edziennik.eDziennik.server.ratingsubjectstudent.domain.dto.usecase.studentratings;

import lombok.Getter;
import pl.edziennik.eDziennik.server.rating.domain.dto.RatingResponseApiDto;

import java.util.List;

@Getter
public class SubjectForRatingsDto {

    private Long id;
    private String name;
    private String description;
    private String teacherFullName;

    private List<RatingResponseApiDto> ratings;

    public SubjectForRatingsDto(Long id, String name, String description, String teacherFullName, List<RatingResponseApiDto> ratings) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.teacherFullName = teacherFullName;
        this.ratings = ratings;
    }
}
