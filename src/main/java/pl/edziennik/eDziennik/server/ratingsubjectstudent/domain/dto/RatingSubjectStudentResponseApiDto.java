package pl.edziennik.eDziennik.server.ratingsubjectstudent.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.edziennik.eDziennik.server.rating.domain.dto.RatingResponseApiDto;
import pl.edziennik.eDziennik.server.student.domain.dto.StudentResponseApiDto;
import pl.edziennik.eDziennik.server.subject.domain.dto.SubjectResponseApiDto;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RatingSubjectStudentResponseApiDto {

    private Long id;

    private StudentResponseApiDto student;
    private SubjectResponseApiDto subject;
    private RatingResponseApiDto rating;

}
