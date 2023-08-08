package pl.edziennik.infrastructure.spring.cache;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import pl.edziennik.common.valueobject.id.UserId;
import pl.edziennik.infrastructure.authentication.security.LoggedUser;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SpringCacheService {

    public static final String LOGGED_USERS_KEY = "loggedUsers";

    private final RedisTemplate<String, Object> redisTemplate;

    public void addLoggedUser(LoggedUser loggedUser) {
        if (Boolean.TRUE.equals(redisTemplate.hasKey(LOGGED_USERS_KEY))) {
            Set<LoggedUser> loggedUsersSet = redisTemplate.opsForSet().members(LOGGED_USERS_KEY).stream()
                    .map(obj -> (LoggedUser) obj)
                    .collect(Collectors.toSet());

            loggedUsersSet.removeIf(user -> user.userId().equals(loggedUser.userId()));
            loggedUsersSet.add(loggedUser);

            redisTemplate.opsForSet().getOperations().delete(LOGGED_USERS_KEY);

            loggedUsersSet.forEach(user -> redisTemplate.opsForSet().add(LOGGED_USERS_KEY, user));
        } else {
            redisTemplate.opsForSet().add(LOGGED_USERS_KEY, loggedUser);
            redisTemplate.expire(LOGGED_USERS_KEY, 1, TimeUnit.HOURS);
        }
    }

    public void deleteLoggedUser(LoggedUser loggedUser) {
        if (Boolean.TRUE.equals(redisTemplate.hasKey(LOGGED_USERS_KEY))) {
            Set<LoggedUser> loggedUsersSet = redisTemplate.opsForSet().members(LOGGED_USERS_KEY).stream()
                    .map(obj -> (LoggedUser) obj)
                    .collect(Collectors.toSet());

            loggedUsersSet.removeIf(user -> user.userId().equals(loggedUser.userId()));

            redisTemplate.opsForSet().getOperations().delete(LOGGED_USERS_KEY);
            loggedUsersSet.forEach(user -> redisTemplate.opsForSet().add(LOGGED_USERS_KEY, user));
        }
    }

    public List<UserId> getLoggedUsers() {
        Set<LoggedUser> loggedUsersSet = redisTemplate.opsForSet().members(LOGGED_USERS_KEY).stream()
                .map(obj -> (LoggedUser) obj)
                .collect(Collectors.toSet());


        List<LoggedUser> list = loggedUsersSet.stream()
                .filter(loggedUser -> LocalDateTime.now().isBefore(LocalDateTime.ofInstant(loggedUser.expirationDate().toInstant(), ZoneId.systemDefault())))
                .toList();


        return list.stream()
                .map(LoggedUser::userId)
                .map(UserId::of)
                .toList();


    }

}
