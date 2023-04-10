package pl.edziennik.eDziennik.domain.parent.domain.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
public record ParentSimpleResponseApiDto(
        Long id,
        String fullName
) {

}
