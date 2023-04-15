package pl.edziennik.eDziennik.domain.admin.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Builder;
import pl.edziennik.eDziennik.domain.admin.domain.wrapper.AdminId;
import pl.edziennik.eDziennik.domain.user.domain.wrapper.UserId;

@Builder
public record AdminResponseApiDto(
        @JsonUnwrapped
        AdminId adminId,
        @JsonUnwrapped
        UserId userId,
        String username,
        String email,
        String role
) {


}
