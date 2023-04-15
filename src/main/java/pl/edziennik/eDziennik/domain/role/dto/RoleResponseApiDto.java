package pl.edziennik.eDziennik.domain.role.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Builder;
import pl.edziennik.eDziennik.domain.role.domain.wrapper.RoleId;

@Builder
public record RoleResponseApiDto(
        @JsonUnwrapped
        RoleId roleId,
        String name) {

}
