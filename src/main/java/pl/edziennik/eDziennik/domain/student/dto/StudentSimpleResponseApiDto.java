package pl.edziennik.eDziennik.domain.student.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
public record StudentSimpleResponseApiDto(
        Long id,
        String fullName
) {

}
