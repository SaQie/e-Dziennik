package pl.edziennik.eDziennik.domain.schoolclass.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
public record SchoolClassSimpleResponseApiDto(
        Long id,
        String className) {

}
