package pl.edziennik.eDziennik.server.ratingsubjectstudent;

import pl.edziennik.eDziennik.server.ratingsubjectstudent.domain.dto.RatingSubjectStudentRequestApiDto;
import pl.edziennik.eDziennik.server.ratingsubjectstudent.domain.dto.RatingSubjectStudentResponseApiDto;

public interface RatingSubjectStudentService {

    RatingSubjectStudentResponseApiDto assignRatingToStudentSubject(final RatingSubjectStudentRequestApiDto dto);

    RatingSubjectStudentResponseApiDto findStudenSubjectRating(final Long id);

    void deleteStudentSubjectRating(final Long id);

}
