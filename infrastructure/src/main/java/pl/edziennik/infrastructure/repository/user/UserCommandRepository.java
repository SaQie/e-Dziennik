package pl.edziennik.infrastructure.repository.user;

import org.springframework.data.repository.RepositoryDefinition;
import pl.edziennik.common.valueobject.Email;
import pl.edziennik.common.valueobject.Username;
import pl.edziennik.common.valueobject.id.UserId;
import pl.edziennik.domain.user.User;

@RepositoryDefinition(domainClass = User.class, idClass = UserId.class)
public interface UserCommandRepository {

    boolean existsByEmail(Email email);

    boolean existsByUsername(Username username);


}
