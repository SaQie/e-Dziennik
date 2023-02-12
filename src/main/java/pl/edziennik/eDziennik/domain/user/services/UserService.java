package pl.edziennik.eDziennik.domain.user.services;

import pl.edziennik.eDziennik.domain.user.domain.User;
import pl.edziennik.eDziennik.domain.user.dto.UserRequestDto;

public interface UserService {

    User createUser(UserRequestDto dto);

    void updateUserLastLoginDate(String username);

}
