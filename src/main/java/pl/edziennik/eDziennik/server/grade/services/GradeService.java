package pl.edziennik.eDziennik.server.grade.services;

import pl.edziennik.eDziennik.server.grade.domain.dto.GradeRequestApiDto;
import pl.edziennik.eDziennik.server.grade.domain.dto.GradeResponseApiDto;

import java.util.List;

public interface GradeService {


    GradeResponseApiDto addNewGrade(final GradeRequestApiDto dto);

    GradeResponseApiDto findRatingById(final Long id);

    void deleteRatingById(final Long id);

    List<GradeResponseApiDto> findAllRatings();
}
