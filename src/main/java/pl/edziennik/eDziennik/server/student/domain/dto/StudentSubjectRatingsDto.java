package pl.edziennik.eDziennik.server.student.domain.dto;

import pl.edziennik.eDziennik.server.rating.domain.dto.RatingResponseApiDto;
import pl.edziennik.eDziennik.server.subject.domain.dto.SubjectResponseApiDto;

import java.util.List;

public class StudentSubjectRatingsDto {

    private StudentResponseApiDto student;
    private SubjectResponseApiDto subject;
    private List<RatingResponseApiDto> ratings;

    public StudentSubjectRatingsDto(StudentResponseApiDto student, SubjectResponseApiDto subject, List<RatingResponseApiDto> ratings) {
        this.student = student;
        this.subject = subject;
        this.ratings = ratings;
    }
}
