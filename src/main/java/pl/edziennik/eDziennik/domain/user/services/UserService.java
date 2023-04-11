package pl.edziennik.eDziennik.domain.user.services;

import pl.edziennik.eDziennik.domain.user.domain.User;
import pl.edziennik.eDziennik.domain.user.domain.wrapper.UserId;
import pl.edziennik.eDziennik.domain.user.dto.UserRequestDto;

public interface UserService {

    User createUser(final UserRequestDto dto);

    void updateUserLastLoginDate(final String username);

    void updateUser(final UserId userId, final UserRequestDto dto);
}
