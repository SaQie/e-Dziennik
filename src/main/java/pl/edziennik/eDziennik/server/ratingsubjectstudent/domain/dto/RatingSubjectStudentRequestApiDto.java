package pl.edziennik.eDziennik.server.ratingsubjectstudent.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RatingSubjectStudentRequestApiDto {

    private Long rating;
    private Long subject;
    private Long student;

}
