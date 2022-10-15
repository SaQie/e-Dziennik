package pl.edziennik.eDziennik.server.student.domain.dto;

import pl.edziennik.eDziennik.server.rating.domain.dto.RatingResponseApiDto;
import pl.edziennik.eDziennik.server.subject.domain.dto.SubjectResponseApiDto;

import java.util.List;

public class StudentAllSubjectsRatingsDto {

    private StudentResponseApiDto student;
    private List<SubjectResponseApiDto> subject;
    private List<RatingResponseApiDto> rating;

}
