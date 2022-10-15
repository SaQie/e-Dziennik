package pl.edziennik.eDziennik.server.ratingsubjectstudent.domain.dto.usecase.studentratings;

import lombok.Getter;
import pl.edziennik.eDziennik.server.rating.domain.dto.RatingResponseApiDto;

import java.util.List;

@Getter
public class SubjectRatingsDto {

    private StudentForRatingsDto student;
    private SubjectForRatingsDto subject;


    public SubjectRatingsDto(StudentForRatingsDto student, SubjectForRatingsDto subject) {
        this.student = student;
        this.subject = subject;
    }
}
