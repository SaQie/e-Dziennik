package pl.edziennik.eDziennik.domain.role.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
public record RoleResponseApiDto(
        Long id,
        String name) {

}
