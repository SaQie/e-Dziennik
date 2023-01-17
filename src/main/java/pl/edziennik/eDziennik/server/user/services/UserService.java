package pl.edziennik.eDziennik.server.user.services;

import pl.edziennik.eDziennik.server.user.domain.User;
import pl.edziennik.eDziennik.server.user.domain.dto.UserRequestDto;

public interface UserService {

    User createUser(UserRequestDto dto);

    void updateUserLastLoginDate(String username);

}
