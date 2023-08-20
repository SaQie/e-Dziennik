package pl.edziennik.infrastructure.repository.token;

import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import pl.edziennik.common.valueobject.vo.Token;
import pl.edziennik.common.valueobject.id.UserId;
import pl.edziennik.infrastructure.spring.ResourceCreator;
import pl.edziennik.infrastructure.spring.exception.BusinessException;
import pl.edziennik.infrastructure.validator.ValidationError;
import pl.edziennik.infrastructure.validator.errorcode.ErrorCode;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
@AllArgsConstructor
class ActivationTokenRepositoryImpl implements ActivationTokenRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final ResourceCreator res;

    private static final String GET_USER_ID_BY_TOKEN =
            "SELECT eac.user_id FROM email_activation_tokens eac where eac.token = :token";

    private static final String GET_USER_ID_WITH_EXPIRED_TOKENS =
            "SELECT eac.user_id FROM email_activation_tokens eac WHERE eac.expiration_date <= CURRENT_TIMESTAMP";

    private static final String DELETE_ACTIVATION_TOKEN =
            "DELETE FROM email_activation_tokens eac WHERE eac.user_id = :userId";

    private static final String DELETE_ACTIVATION_TOKENS =
            "DELETE FROM email_activation_tokens eac WHERE eac.user_id IN (:userIds)";

    private static final String INSERT_ACTIVATION_TOKEN =
            "INSERT INTO email_activation_tokens(user_id, token, expiration_date) VALUES (:userId, :token, :expiration_date) ";

    private static final String CHECK_ACTIVATION_TOKEN_EXP_DATE =
            "SELECT CASE WHEN COUNT(eac) > 0 THEN TRUE ELSE FALSE END FROM email_activation_tokens eac WHERE eac.token = :token AND eac.expiration_date >= current_date()";

    public UserId getUserByActivationToken(Token token) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource("token", token.value());
        try {
            String response = jdbcTemplate.queryForObject(GET_USER_ID_BY_TOKEN, parameterSource, String.class);
            return UserId.of(response);
        } catch (EmptyResultDataAccessException e) {
            throw new BusinessException(
                    new ValidationError("token", res.of("token.not.found", token), ErrorCode.INVALID_ACTIVATION_TOKEN.errorCode()
                    ));
        }
    }

    public boolean checkActivationTokenIsExpired(Token token) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource("token", token.value());
        return jdbcTemplate.queryForObject(CHECK_ACTIVATION_TOKEN_EXP_DATE, parameterSource, Boolean.class);
    }

    public void deleteActivationToken(UserId userId) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource("userId", userId.id());
        jdbcTemplate.update(DELETE_ACTIVATION_TOKEN, parameterSource);
    }

    public void deleteActivationToken(List<UserId> userIds) {
        List<UUID> uuidList = userIds.stream()
                .map(UserId::id)
                .toList();
        MapSqlParameterSource parameterSource = new MapSqlParameterSource("userIds", uuidList);
        jdbcTemplate.update(DELETE_ACTIVATION_TOKENS, parameterSource);
    }

    public void insertActivationToken(UserId userId, UUID token) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("userId", userId.id());
        parameterSource.addValue("token", token);
        parameterSource.addValue("expiration_date", LocalDateTime.now().plusDays(2));
        jdbcTemplate.update(INSERT_ACTIVATION_TOKEN, parameterSource);
    }

    public List<UserId> getUserIdsWithExpiredActivationTokens() {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        List<String> response = jdbcTemplate.queryForList(GET_USER_ID_WITH_EXPIRED_TOKENS, parameterSource, String.class);
        return response.stream()
                .map(UserId::of)
                .toList();
    }

}
