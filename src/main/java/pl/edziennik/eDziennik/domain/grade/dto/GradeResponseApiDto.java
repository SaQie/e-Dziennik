package pl.edziennik.eDziennik.domain.grade.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Builder;
import pl.edziennik.eDziennik.domain.grade.domain.wrapper.GradeId;

@Builder
public record GradeResponseApiDto(
    @JsonUnwrapped
    GradeId gradeId,
    Integer grade,
    Integer weight,
    String description
    )
{
}
