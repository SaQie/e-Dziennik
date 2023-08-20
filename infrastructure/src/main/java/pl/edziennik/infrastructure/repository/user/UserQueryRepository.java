package pl.edziennik.infrastructure.repository.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;
import pl.edziennik.common.valueobject.id.UserId;
import pl.edziennik.common.valueobject.vo.FullName;
import pl.edziennik.common.valueobject.vo.Name;
import pl.edziennik.common.view.user.LoggedUserView;
import pl.edziennik.domain.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RepositoryDefinition(domainClass = User.class, idClass = UserId.class)
public interface UserQueryRepository {

    // Todo pomysl nad przeniesieniem zapytan do zmiennych, sprobuj zrobic tutaj prywatna klase zagniezdzona
    String GET_LOGGED_USERS_QUERY =
            """
                    SELECT t.full_name, u.id, r.name, u.username FROM users u
                    JOIN teacher t ON (t.user_id = u.id)
                    JOIN "role" r ON (r.id = u.role_id)
                    WHERE u.id IN (:userIds)
                    AND u.username <> :username
                    UNION ALL
                    SELECT 'administrator',u.id, r.name, u.username FROM users u
                    JOIN admin a ON (a.user_id = u.id)
                    JOIN "role" r ON (r.id = u.role_id)
                    WHERE u.id IN (:userIds)
                    AND u.username <> :username
                    UNION ALL
                    SELECT s.full_name,u.id, r.name,u.username  FROM users u
                    JOIN student s  ON (s.user_id = u.id)
                    JOIN "role" r ON (r.id = u.role_id)
                    WHERE u.id IN (:userIds)
                    AND u.username <> :username
                    UNION ALL
                    SELECT p.full_name, u.id, r.name,u.username  FROM users u
                    JOIN parent p   ON (p.user_id = u.id)
                    JOIN "role" r ON (r.id = u.role_id)
                    WHERE u.id IN (:userIds)
                    AND u.username <> :username 
                    """;




    @Query(value = GET_LOGGED_USERS_QUERY, nativeQuery = true)
    List<Object[]> getLoggedUsersData(@Param(value = "userIds") List<UUID> userIds, @Param("username") String usernameToExclude);


    default List<LoggedUserView> getLoggedUsersView(List<UserId> userIds, String usernameToExclude) {
        List<UUID> rawUserIds = userIds.stream()
                .map(UserId::id)
                .toList();

        List<Object[]> loggedUsersData = this.getLoggedUsersData(rawUserIds, usernameToExclude);
        List<LoggedUserView> userDtos = new ArrayList<>();


        for (Object[] row : loggedUsersData) {

            String fullname = (String) row[0];
            UUID userId = (UUID) row[1];
            String roleName = (String) row[2];
            String username = (String) row[3];

            LoggedUserView dto = new LoggedUserView(UserId.of(userId), Name.of(username),FullName.of(fullname), Name.of(roleName));
            userDtos.add(dto);
        }

        return userDtos;
    }

}
