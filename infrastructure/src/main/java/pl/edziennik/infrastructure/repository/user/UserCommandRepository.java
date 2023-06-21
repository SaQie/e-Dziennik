package pl.edziennik.infrastructure.repository.user;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.valueobject.Email;
import pl.edziennik.common.valueobject.Pesel;
import pl.edziennik.common.valueobject.Username;
import pl.edziennik.common.valueobject.id.UserId;
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

    User getUserByUserId(UserId userId);
}