package pl.edziennik.application.command.user.activate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.edziennik.application.BaseUnitTest;
import pl.edziennik.common.valueobject.id.UserId;
import pl.edziennik.common.valueobject.vo.Token;
import pl.edziennik.infrastructure.spring.exception.BusinessException;
import pl.edziennik.infrastructure.validator.ValidationError;
import pl.edziennik.infrastructure.validator.errorcode.ErrorCode;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ActivateUserCommandValidatorTest extends BaseUnitTest {

    private final ActivateUserCommandValidator validator;

    public ActivateUserCommandValidatorTest() {
        this.validator = new ActivateUserCommandValidator(activationTokenRepository);
    }

    @Test
    public void shouldAddErrorWhenTokenIsExpired() {
        // given
        UserId userId = UserId.create();
        UUID token = UUID.randomUUID();
        activationTokenRepository.insertActivationToken(userId, token);

        ActivateUserCommand command = new ActivateUserCommand(Token.of(token));
        // when
        validator.validate(command, validationErrorBuilder);

        // then
        List<ValidationError> errors = validationErrorBuilder.getErrors();
        assertEquals(errors.size(), 1);
        assertEquals(errors.get(0).errorCode(), ErrorCode.ACTIVATION_TOKEN_EXPIRED.errorCode());
    }

    @Test
    public void shouldThrowExceptionWhenTokenNotExists() {
        // given
        ActivateUserCommand command = new ActivateUserCommand(Token.of(UUID.randomUUID()));

        // when
        // then
        Assertions.assertThatThrownBy(() -> validator.validate(command, validationErrorBuilder))
                .isInstanceOf(BusinessException.class);

    }

}
