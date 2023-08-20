package pl.edziennik.infrastructure.repository.token;

import pl.edziennik.common.valueobject.vo.Token;
import pl.edziennik.common.valueobject.id.UserId;

import java.util.List;
import java.util.UUID;

public interface ActivationTokenRepository {

    UserId getUserByActivationToken(Token token);

    boolean checkActivationTokenIsExpired(Token token);

    void deleteActivationToken(UserId userId);

    void deleteActivationToken(List<UserId> userIds);

    void insertActivationToken(UserId userId, UUID token);

    List<UserId> getUserIdsWithExpiredActivationTokens();
}
