package pl.edziennik.eDziennik.infrastructure;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.domain.user.domain.User;
import pl.edziennik.eDziennik.domain.user.repository.UserRepository;
import pl.edziennik.eDziennik.infrastructure.spring.query.IQueryHandler;

@Service
@AllArgsConstructor
public class GetUserQueryHandler implements IQueryHandler<GetUserQuery, User> {

    private final UserRepository userRepository;

    @Override
    public User handle(GetUserQuery query) {
        return null;
    }
}
