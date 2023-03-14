package pl.edziennik.eDziennik.domain.admin.dto;

import lombok.Builder;
import lombok.Getter;
import pl.edziennik.eDziennik.domain.role.domain.Role;

@Getter
@Builder
public class AdminResponseApiDto {

    private final Long id;
    private final Long userId;

    private final String username;
    private final String email;
    private final String role;

}
