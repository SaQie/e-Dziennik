package pl.edziennik.application.mock.repositories;

import pl.edziennik.common.valueobject.Token;
import pl.edziennik.common.valueobject.id.UserId;
import pl.edziennik.infrastructure.repository.token.ActivationTokenRepository;

import java.util.*;

public class ActivationTokenMockRepository implements ActivationTokenRepository {

    private final Map<Token, UserId> database;

    public ActivationTokenMockRepository() {
        this.database = new HashMap<>();
    }

    @Override
    public UserId getUserByActivationToken(Token token) {
        return database.get(token);
    }

    @Override
    public boolean checkActivationTokenIsExpired(Token token) {
        return true;
    }

    @Override
    public void deleteActivationToken(UserId userId) {

    }

    @Override
    public void deleteActivationToken(List<UserId> userIds) {
    }

    @Override
    public void insertActivationToken(UserId userId, UUID token) {
        database.put(Token.of(token), userId);
    }

    @Override
    public List<UserId> getUserIdsWithExpiredActivationTokens() {
        return Collections.emptyList();
    }
}
