package pl.edziennik.application.mock.repositories;

import pl.edziennik.common.valueobject.Email;
import pl.edziennik.common.valueobject.Username;
import pl.edziennik.common.valueobject.id.UserId;
import pl.edziennik.domain.user.User;
import pl.edziennik.infrastructure.repositories.user.UserCommandRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserCommandMockRepo implements UserCommandRepository {

    private final Map<UserId, User> database;

    public UserCommandMockRepo() {
        this.database = new HashMap<>();
    }

    @Override
    public boolean existsByEmail(Email email) {
        List<User> users = database.values().stream()
                .filter(item -> item.getEmail().equals(email))
                .toList();
        return !users.isEmpty();
    }

    @Override
    public boolean existsByUsername(Username username) {
        List<User> users = database.values().stream()
                .filter(item -> item.getUsername().equals(username))
                .toList();
        return !users.isEmpty();
    }
}
