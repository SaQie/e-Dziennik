package pl.edziennik.application.query.users;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import pl.edziennik.common.valueobject.id.UserId;
import pl.edziennik.common.view.user.LoggedUserView;
import pl.edziennik.infrastructure.repository.user.UserQueryRepository;
import pl.edziennik.infrastructure.spring.cache.SpringCacheService;

import java.util.List;

@Repository
@AllArgsConstructor
class UsersQueryDaoImpl implements UsersQueryDao {

    private final SpringCacheService springCacheService;
    private final UserQueryRepository userQueryRepository;

    @Override
    public List<LoggedUserView> getLoggedUsersView() {
        List<UserId> loggedUsers = springCacheService.getLoggedUsers();

        // Must remove actual logged in user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return userQueryRepository.getLoggedUsersView(loggedUsers, authentication.getName());
    }
}