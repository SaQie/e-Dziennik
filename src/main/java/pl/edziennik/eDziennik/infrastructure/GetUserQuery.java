package pl.edziennik.eDziennik.infrastructure;

import pl.edziennik.eDziennik.domain.user.domain.User;
import pl.edziennik.eDziennik.infrastructure.spring.base.HandledBy;
import pl.edziennik.eDziennik.infrastructure.spring.query.IQuery;

@HandledBy(handler = GetUserQueryHandler.class)
public class GetUserQuery implements IQuery<User> {
}
