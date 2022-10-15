package pl.edziennik.eDziennik.server.ratingsubjectstudent.domain.dto.mapper;

import pl.edziennik.eDziennik.server.rating.domain.dto.mapper.RatingMapper;
import pl.edziennik.eDziennik.server.ratingsubjectstudent.domain.RatingSubjectStudentLink;
import pl.edziennik.eDziennik.server.ratingsubjectstudent.domain.dto.RatingSubjectStudentResponseApiDto;
import pl.edziennik.eDziennik.server.ratingsubjectstudent.domain.dto.usecase.studentratings.StudentForRatingsDto;
import pl.edziennik.eDziennik.server.ratingsubjectstudent.domain.dto.usecase.studentratings.SubjectForRatingsDto;
import pl.edziennik.eDziennik.server.ratingsubjectstudent.domain.dto.usecase.studentratings.SubjectRatingsDto;
import pl.edziennik.eDziennik.server.student.domain.dto.mapper.StudentMapper;
import pl.edziennik.eDziennik.server.subject.domain.dto.mapper.SubjectMapper;

import java.util.List;

public class RatingSubjectStudentMapper {

    private RatingSubjectStudentMapper() {
    }

    public static RatingSubjectStudentResponseApiDto toDto(RatingSubjectStudentLink entity) {
        return new RatingSubjectStudentResponseApiDto(
                entity.getId(),
                StudentMapper.toDto(entity.getStudent()),
                SubjectMapper.toDto(entity.getSubjects().get(0)),
                RatingMapper.toDto(entity.getRatings().get(0))
        );
    }

}
