package pl.edziennik.eDziennik.infrastructure.repository.query.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edziennik.eDziennik.domain.user.domain.User;
import pl.edziennik.eDziennik.domain.user.domain.wrapper.UserId;

@Repository
public interface UserQueryRepository extends JpaRepository<User, UserId> {
}
