package pl.edziennik.infrastructure.repositories.user;

import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.valueobject.Email;
import pl.edziennik.common.valueobject.Regon;
import pl.edziennik.domain.user.User;
import pl.edziennik.domain.user.UserId;

@RepositoryDefinition(domainClass = User.class, idClass = UserId.class)
public interface UserCommandRepository {

    boolean existsByEmail(Email email);

    boolean existsByUsername(Regon username);


}
