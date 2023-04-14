package pl.edziennik.eDziennik.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.edziennik.eDziennik.domain.user.domain.User;
import pl.edziennik.eDziennik.domain.user.domain.wrapper.UserId;

@Repository
public interface UserRepository extends JpaRepository<User, UserId> {

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    User getByEmail(String email);

    @Query("SELECT u FROM User u " +
            "JOIN FETCH u.role " +
            "WHERE u.username = :username")
    User getByUsername(String username);

}
