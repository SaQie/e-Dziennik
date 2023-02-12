package pl.edziennik.eDziennik.domain.grade.service;

import pl.edziennik.eDziennik.domain.grade.dto.GradeRequestApiDto;
import pl.edziennik.eDziennik.domain.grade.dto.GradeResponseApiDto;

import java.util.List;

public interface GradeService {


    GradeResponseApiDto addNewGrade(final GradeRequestApiDto dto);

    GradeResponseApiDto updateGrade(final Long id, final GradeRequestApiDto dto);

    GradeResponseApiDto findGradeById(final Long id);

    void deleteGradeById(final Long id);

    List<GradeResponseApiDto> findAllGrades();

}
