package pl.edziennik.application.query.users;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.query.IQueryHandler;
import pl.edziennik.common.dto.user.LoggedUserDto;
import pl.edziennik.common.valueobject.id.UserId;
import pl.edziennik.infrastructure.repository.user.UserQueryRepository;
import pl.edziennik.infrastructure.spring.cache.SpringCacheService;

import java.util.List;

@Component
@AllArgsConstructor
class GetLoggedUsersQueryHandler implements IQueryHandler<GetLoggedUsersQuery, List<LoggedUserDto>> {

    private final SpringCacheService springCacheService;
    private final UserQueryRepository userQueryRepository;

    @Override
    public List<LoggedUserDto> handle(GetLoggedUsersQuery command) {
        List<UserId> loggedUsers = springCacheService.getLoggedUsers();

        // Must remove actual logged in user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return userQueryRepository.getLoggedUsersDto(loggedUsers, authentication.getName());
    }
}