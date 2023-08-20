package pl.edziennik.infrastructure.repository.user;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.valueobject.id.UserId;
import pl.edziennik.common.valueobject.vo.Email;
import pl.edziennik.common.valueobject.vo.Pesel;
import pl.edziennik.common.valueobject.vo.Username;
import pl.edziennik.domain.user.User;

import java.util.List;
import java.util.Optional;

@RepositoryDefinition(domainClass = User.class, idClass = UserId.class)
public interface UserCommandRepository {

    boolean existsByEmail(Email email);

    boolean existsByUsername(Username username);

    boolean existsByPesel(Pesel pesel);

    @Query("DELETE FROM User u WHERE u.userId IN (:userIds)")
    @Modifying
    void deleteAll(List<UserId> userIds);

    Optional<User> findById(UserId userId);

    User save(User user);

    @Query("SELECT u FROM User u " +
            "JOIN FETCH u.role " +
            "WHERE u.username = :username")
    User getByUsername(Username username);

    @Query("SELECT u FROM User u " +
            "JOIN FETCH u.role " +
            "WHERE u.pesel = :pesel")
    User getByPesel(Pesel pesel);

    User getUserByUserId(UserId userId);
}
