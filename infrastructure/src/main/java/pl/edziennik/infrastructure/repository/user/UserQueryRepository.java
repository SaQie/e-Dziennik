package pl.edziennik.infrastructure.repository.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.valueobject.Pesel;
import pl.edziennik.common.valueobject.Username;
import pl.edziennik.common.valueobject.id.UserId;
import pl.edziennik.domain.user.User;

import java.util.Optional;

@RepositoryDefinition(domainClass = User.class, idClass = UserId.class)
public interface UserQueryRepository {

    @Query("SELECT u FROM User u " +
            "JOIN FETCH u.role " +
            "WHERE u.username = :username")
    User getByUsername(Username username);

    @Query("SELECT u FROM User u " +
            "JOIN FETCH u.role " +
            "WHERE u.pesel = :pesel")
    User getByPesel(Pesel pesel);

    Optional<User> findById(UserId userId);

    User getUserByUserId(UserId userId);
}
