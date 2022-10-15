package pl.edziennik.eDziennik.server.ratingsubjectstudent.domain.dto.usecase.studentratings;

import lombok.Getter;

@Getter
public class StudentForRatingsDto {

    private Long id;
    private String fullName;

    public StudentForRatingsDto(Long id, String fullName) {
        this.id = id;
        this.fullName = fullName;
    }
}
