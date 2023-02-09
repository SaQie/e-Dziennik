package pl.edziennik.eDziennik.user;

import pl.edziennik.eDziennik.server.role.domain.Role;
import pl.edziennik.eDziennik.server.user.domain.dto.UserRequestDto;

public class UserIntegrationTestUtil {

    public UserRequestDto prepareRequestDto(String username, String email){
        return UserRequestDto.builder()
                .city("aa")
                .postalCode("www")
                .pesel("1111")
                .lastName("wwwww")
                .firstName("eeeee")
                .email(email)
                .username(username)
                .role(Role.RoleConst.ROLE_TEACHER.name())
                .password("www")
                .build();
    }

}
