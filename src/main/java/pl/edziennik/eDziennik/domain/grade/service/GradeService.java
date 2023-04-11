package pl.edziennik.eDziennik.domain.grade.service;

import pl.edziennik.eDziennik.domain.grade.domain.wrapper.GradeId;
import pl.edziennik.eDziennik.domain.grade.dto.GradeRequestApiDto;
import pl.edziennik.eDziennik.domain.grade.dto.GradeResponseApiDto;

public interface GradeService {


    GradeResponseApiDto addNewGrade(final GradeRequestApiDto dto);

    GradeResponseApiDto updateGrade(final GradeId gradeId, final GradeRequestApiDto dto);

    GradeResponseApiDto findGradeById(final GradeId gradeId);

    void deleteGradeById(final GradeId gradeId);

}
