package pl.edziennik.eDziennik.user;

import pl.edziennik.eDziennik.server.role.domain.Role;
import pl.edziennik.eDziennik.server.user.domain.dto.UserRequestDto;

public class UserIntegrationTestUtil {

    public UserRequestDto prepareRequestDto(String username, String email){
        return new UserRequestDto(username, "xxxxxx", email, Role.RoleConst.ROLE_TEACHER.name());
    }

}
