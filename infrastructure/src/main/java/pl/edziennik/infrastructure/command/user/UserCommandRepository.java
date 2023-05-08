package pl.edziennik.infrastructure.command.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edziennik.domain.user.User;
import pl.edziennik.domain.user.UserId;

@Repository
public interface UserCommandRepository extends JpaRepository<User, UserId> {

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);


}
