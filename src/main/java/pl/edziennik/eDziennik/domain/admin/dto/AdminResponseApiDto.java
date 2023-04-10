package pl.edziennik.eDziennik.domain.admin.dto;

import lombok.Builder;
import lombok.Getter;
import pl.edziennik.eDziennik.domain.role.domain.Role;

@Builder
public record AdminResponseApiDto(
        Long id,
        Long userId,
        String username,
        String email,
        String role
) {



}
