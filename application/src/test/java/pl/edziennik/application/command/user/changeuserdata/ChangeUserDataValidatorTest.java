package pl.edziennik.application.command.user.changeuserdata;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.edziennik.application.BaseUnitTest;
import pl.edziennik.application.mock.repositories.RoleCommandMockRepo;
import pl.edziennik.common.valueobject.vo.Email;
import pl.edziennik.common.valueobject.vo.Username;
import pl.edziennik.common.valueobject.id.UserId;
import pl.edziennik.domain.user.User;
import pl.edziennik.infrastructure.spring.exception.BusinessException;
import pl.edziennik.infrastructure.validator.ValidationError;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChangeUserDataValidatorTest extends BaseUnitTest {

    private final ChangeUserDataCommandValidator validator;

    public ChangeUserDataValidatorTest() {
        this.validator = new ChangeUserDataCommandValidator(userCommandRepository);
    }

    @Test
    public void shouldThrowExceptionWhenUserNotExists() {
        // given
        ChangeUserDataCommand command = new ChangeUserDataCommand(UserId.of(UUID.randomUUID()), Username.of("Test"), Email.of("test@example.com"));

        // when
        // then
        Assertions.assertThatThrownBy(() -> validator.validate(command, validationErrorBuilder))
                .isInstanceOf(BusinessException.class)
                .hasMessage(
                        assertBusinessExceptionMessage(ChangeUserDataCommand.USER_ID));


    }

    @Test
    public void shouldAddErrorWhenUserWithUsernameAlreadyExists() {
        // given
        createUser("Test", "Test@example.com", RoleCommandMockRepo.TEACHER_ROLE_NAME.value());
        User secondUser = createUser("Test1", "Test2@example.com", RoleCommandMockRepo.TEACHER_ROLE_NAME.value());

        ChangeUserDataCommand command = new ChangeUserDataCommand(secondUser.userId(), Username.of("Test"), Email.of("test@example.com"));

        // when
        validator.validate(command, validationErrorBuilder);

        // then
        List<ValidationError> errors = validationErrorBuilder.getErrors();
        assertEquals(errors.size(), 1);
        assertEquals(errors.get(0).field(), ChangeUserDataCommand.USERNAME);
        assertEquals(errors.get(0).message(), ChangeUserDataCommandValidator.MESSAGE_KEY_USER_ALREADY_EXISTS_BY_USERNAME);

    }

    @Test
    public void shouldAddErrorWhenUserWithEmailAlreadyExists() {
        // given
        createUser("Test", "Test@example.com", RoleCommandMockRepo.TEACHER_ROLE_NAME.value());
        User secondUser = createUser("Test1", "Test2@example.com", RoleCommandMockRepo.TEACHER_ROLE_NAME.value());

        ChangeUserDataCommand command = new ChangeUserDataCommand(secondUser.userId(), Username.of("Test5"), Email.of("Test@example.com"));

        // when
        validator.validate(command, validationErrorBuilder);

        // then
        List<ValidationError> errors = validationErrorBuilder.getErrors();
        assertEquals(errors.size(), 1);
        assertEquals(errors.get(0).field(), ChangeUserDataCommand.EMAIL);
        assertEquals(errors.get(0).message(), ChangeUserDataCommandValidator.MESSAGE_KEY_USER_ALREADY_EXISTS_BY_EMAIL);
    }

}
