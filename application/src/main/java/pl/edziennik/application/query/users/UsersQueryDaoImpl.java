package pl.edziennik.application.query.users;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import pl.edziennik.common.valueobject.id.UserId;
import pl.edziennik.common.view.user.LoggedUserView;
import pl.edziennik.infrastructure.repository.user.UserQueryRepository;
import pl.edziennik.infrastructure.spring.cache.SpringCacheService;

import java.util.List;

@Repository
class UsersQueryDaoImpl implements UsersQueryDao {

    // Unfortunately I needed to "dirty" my code with that autowired because
    // on alwaysdata i don't have access to redis(prod profile)
    @Autowired(required = false)
    private SpringCacheService springCacheService;

    @Autowired
    private UserQueryRepository userQueryRepository;



    @Override
    public List<LoggedUserView> getLoggedUsersView() {
        List<UserId> loggedUsers = springCacheService.getLoggedUsers();

        // Must remove actual logged in user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return userQueryRepository.getLoggedUsersView(loggedUsers, authentication.getName());
    }
}
