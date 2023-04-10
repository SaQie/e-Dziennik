package pl.edziennik.eDziennik.domain.school.dto;

import lombok.Builder;

@Builder
public record SchoolSimpleResponseApiDto(
        Long id,
        String name) {

}
