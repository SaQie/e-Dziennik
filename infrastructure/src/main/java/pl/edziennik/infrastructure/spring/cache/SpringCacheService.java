package pl.edziennik.infrastructure.spring.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import pl.edziennik.common.valueobject.id.UserId;
import pl.edziennik.infrastructure.authentication.security.LoggedUser;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SpringCacheService {

    public static final String LOGGED_USERS_KEY = "loggedUsers";

    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public SpringCacheService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void addLoggedUser(LoggedUser loggedUser) {
        if (Boolean.TRUE.equals(redisTemplate.hasKey(LOGGED_USERS_KEY))) {
            Set<LoggedUser> loggedUsersSet = redisTemplate.opsForSet().members(LOGGED_USERS_KEY).stream()
                    .filter(obj -> obj instanceof LoggedUser)
                    .map(obj -> (LoggedUser) obj)
                    .collect(Collectors.toSet());

            loggedUsersSet.removeIf(user -> user.getUserId().equals(loggedUser.getUserId()));
            loggedUsersSet.add(loggedUser);

            redisTemplate.opsForSet().getOperations().delete(LOGGED_USERS_KEY);

            loggedUsersSet.forEach(user -> redisTemplate.opsForSet().add(LOGGED_USERS_KEY, user));


        } else {
            Set<LoggedUser> newLoggedUsersSet = new HashSet<>();
            newLoggedUsersSet.add(loggedUser);

            redisTemplate.opsForSet().add(LOGGED_USERS_KEY, loggedUser);
        }
    }

    public void deleteLoggedUser(LoggedUser loggedUser) {
        if (Boolean.TRUE.equals(redisTemplate.hasKey(LOGGED_USERS_KEY))) {
            Set<LoggedUser> loggedUsersSet = redisTemplate.opsForSet().members(LOGGED_USERS_KEY).stream()
                    .filter(obj -> obj instanceof LoggedUser)
                    .map(obj -> (LoggedUser) obj)
                    .collect(Collectors.toSet());
            loggedUsersSet.removeIf(user -> user.getUserId().equals(loggedUser.getUserId()));

            redisTemplate.opsForSet().getOperations().delete(LOGGED_USERS_KEY);
            loggedUsersSet.forEach(user -> redisTemplate.opsForSet().add(LOGGED_USERS_KEY, user));
        }
    }

    public List<UserId> getLoggedUsers() {
        Set<LoggedUser> loggedUsersSet = redisTemplate.opsForSet().members(LOGGED_USERS_KEY).stream()
                .filter(obj -> obj instanceof LoggedUser)
                .map(obj -> (LoggedUser) obj)
                .collect(Collectors.toSet());


        List<LoggedUser> list = loggedUsersSet.stream()
                .filter(loggedUser -> LocalDateTime.now().isBefore(LocalDateTime.ofInstant(loggedUser.getExpirationDate().toInstant(), ZoneId.systemDefault())))
                .toList();


        return list.stream()
                .map(LoggedUser::getUserId)
                .map(UserId::of)
                .toList();


    }

}
