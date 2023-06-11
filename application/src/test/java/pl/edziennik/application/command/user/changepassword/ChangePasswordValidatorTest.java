package pl.edziennik.application.command.user.changepassword;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.edziennik.application.BaseUnitTest;
import pl.edziennik.application.mock.repositories.RoleCommandMockRepo;
import pl.edziennik.common.valueobject.Password;
import pl.edziennik.common.valueobject.id.UserId;
import pl.edziennik.domain.user.User;
import pl.edziennik.infrastructure.spring.exception.BusinessException;
import pl.edziennik.infrastructure.validator.ValidationError;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChangePasswordValidatorTest extends BaseUnitTest {

    private final ChangePasswordCommandValidator validator;

    public ChangePasswordValidatorTest() {
        this.validator = new ChangePasswordCommandValidator(passwordEncoder, userCommandRepository);
    }

    @Test
    public void shouldThrowExceptionWhenUserNotExists() {
        // given
        ChangePasswordCommand command = new ChangePasswordCommand(
                UserId.of(UUID.randomUUID()),
                Password.of("Test"),
                Password.of("Test2"));

        // when
        // then
        Assertions.assertThatThrownBy(() -> validator.validate(command, validationErrorBuilder))
                .isInstanceOf(BusinessException.class)
                .hasMessage(
                        assertBusinessExceptionMessage(ChangePasswordCommand.USER_ID));

    }

    @Test
    public void shouldAddErrorWhenOldPasswordIsNotEqualToActualPassword() {
        // given
        User user = createUser("Test", "test@example.com", RoleCommandMockRepo.STUDENT_ROLE_NAME.value(), "Test123");

        ChangePasswordCommand command = new ChangePasswordCommand(user.getUserId(),
                Password.of("Testtttt"),
                Password.of("test12333"));

        // when
        validator.validate(command, validationErrorBuilder);

        // then
        List<ValidationError> errors = validationErrorBuilder.getErrors();
        assertEquals(errors.size(), 1);
        assertEquals(errors.get(0).field(), ChangePasswordCommand.OLD_PASSWORD);
        assertEquals(errors.get(0).message(), ChangePasswordCommandValidator.MESSAGE_KEY_OLD_PASSWORD_IS_INCORRECT);
    }
}
