package pl.edziennik.eDziennik.domain.admin.dto;

import lombok.Getter;
import pl.edziennik.eDziennik.domain.role.domain.Role;

@Getter
public class AdminResponseApiDto {

    private final Long id;
    private final String username;
    private final String email;
    private final String role;

    public AdminResponseApiDto(Long id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = Role.RoleConst.ROLE_ADMIN.name();
    }
}
