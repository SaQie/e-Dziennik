package pl.edziennik.eDziennik.domain.user;

import pl.edziennik.eDziennik.domain.role.domain.Role;
import pl.edziennik.eDziennik.domain.user.dto.UserRequestDto;

public class UserIntegrationTestUtil {

    public UserRequestDto prepareRequestDto(String username, String email){
        return UserRequestDto.builder()
                .email(email)
                .username(username)
                .role(Role.RoleConst.ROLE_TEACHER.name())
                .password("www")
                .build();
    }

}
