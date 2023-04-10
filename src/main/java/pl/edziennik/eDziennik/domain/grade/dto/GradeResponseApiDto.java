package pl.edziennik.eDziennik.domain.grade.dto;

import lombok.Builder;

@Builder
public record GradeResponseApiDto(
    Long id,
    Integer grade,
    Integer weight,
    String description
    )
{
}
