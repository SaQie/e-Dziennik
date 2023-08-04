package pl.edziennik.application.mock.repositories;

import pl.edziennik.common.valueobject.Email;
import pl.edziennik.common.valueobject.Pesel;
import pl.edziennik.common.valueobject.Username;
import pl.edziennik.common.valueobject.id.UserId;
import pl.edziennik.domain.user.User;
import pl.edziennik.infrastructure.repository.user.UserCommandRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserCommandMockRepo implements UserCommandRepository {

    private final Map<UserId, User> database;

    public UserCommandMockRepo() {
        this.database = new HashMap<>();
    }

    @Override
    public boolean existsByEmail(Email email) {
        List<User> users = database.values().stream()
                .filter(item -> item.email().equals(email))
                .toList();
        return !users.isEmpty();
    }

    @Override
    public boolean existsByUsername(Username username) {
        List<User> users = database.values().stream()
                .filter(item -> item.username().equals(username))
                .toList();
        return !users.isEmpty();
    }

    @Override
    public boolean existsByPesel(Pesel pesel) {
        List<User> users = database.values().stream()
                .filter(item -> item.pesel().equals(pesel))
                .toList();
        return !users.isEmpty();
    }

    @Override
    public void deleteAll(List<UserId> userIds) {
        database.clear();
    }

    @Override
    public Optional<User> findById(UserId userId) {
        return Optional.ofNullable(database.get(userId));
    }

    @Override
    public User save(User user) {
        this.database.put(user.userId(), user);
        return database.get(user.userId());
    }

    @Override
    public User getUserByUserId(UserId userId) {
        return database.get(userId);
    }
}
