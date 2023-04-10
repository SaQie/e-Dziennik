package pl.edziennik.eDziennik.domain.schoollevel.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
public record SchoolLevelResponseApiDto(
        Long id,
        String name
) {

}
