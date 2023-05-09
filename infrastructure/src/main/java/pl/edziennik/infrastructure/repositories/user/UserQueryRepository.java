package pl.edziennik.infrastructure.repositories.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.valueobject.Regon;
import pl.edziennik.domain.user.User;
import pl.edziennik.domain.user.UserId;

@RepositoryDefinition(domainClass = User.class, idClass = UserId.class)
public interface UserQueryRepository {

    @Query("SELECT u FROM User u " +
            "JOIN FETCH u.role " +
            "WHERE u.username = :username")
    User getByUsername(Regon username);
}
