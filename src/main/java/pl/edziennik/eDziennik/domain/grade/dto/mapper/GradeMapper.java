package pl.edziennik.eDziennik.domain.grade.dto.mapper;

import pl.edziennik.eDziennik.domain.grade.domain.Grade;
import pl.edziennik.eDziennik.domain.grade.dto.GradeRequestApiDto;
import pl.edziennik.eDziennik.domain.grade.dto.GradeResponseApiDto;

import java.util.List;
import java.util.stream.Collectors;

public class GradeMapper {

    private GradeMapper() {
    }

    public static GradeResponseApiDto toDto(Grade grade) {
        return new GradeResponseApiDto(
                grade.getGradeId().id(),
                grade.getGrade().grade,
                grade.getWeight(),
                grade.getDescription()
        );
    }

    public static List<GradeResponseApiDto> toDto(List<Grade> grades) {
        return grades.stream().map(rating -> new GradeResponseApiDto(
                rating.getGradeId().id(),
                rating.getGrade().grade,
                rating.getWeight(),
                rating.getDescription()
        )).collect(Collectors.toList());
    }

    public static Grade toEntity(GradeRequestApiDto dto){
        return new Grade(
                Grade.GradeConst.getByRating(dto.getGrade()),
                dto.getWeight(),
                dto.getDescription()
        );
    }
}
