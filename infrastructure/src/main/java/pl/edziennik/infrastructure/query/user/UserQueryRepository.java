package pl.edziennik.infrastructure.query.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.edziennik.domain.user.User;
import pl.edziennik.domain.user.UserId;

@Repository
public interface UserQueryRepository extends JpaRepository<User, UserId> {

    @Query("SELECT u FROM User u " +
            "JOIN FETCH u.role " +
            "WHERE u.username = :username")
    User getByUsername(String username);
}
