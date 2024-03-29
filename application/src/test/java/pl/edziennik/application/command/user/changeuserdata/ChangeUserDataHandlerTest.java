package pl.edziennik.application.command.user.changeuserdata;

import org.junit.jupiter.api.Test;
import pl.edziennik.application.BaseUnitTest;
import pl.edziennik.application.mock.repositories.RoleCommandMockRepo;
import pl.edziennik.common.valueobject.vo.Email;
import pl.edziennik.common.valueobject.vo.Username;
import pl.edziennik.domain.user.User;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChangeUserDataHandlerTest extends BaseUnitTest {

    private final ChangeUserDataCommandHandler handler;

    public ChangeUserDataHandlerTest() {
        this.handler = new ChangeUserDataCommandHandler(userCommandRepository);
    }

    @Test
    public void shouldChangeUserEmail() {
        // given
        Email expectedEmailAfterUpdate = Email.of("Test2@example.com");
        User user = createUser("Test", "test@example.com", RoleCommandMockRepo.TEACHER_ROLE_NAME.value());

        ChangeUserDataCommand command = new ChangeUserDataCommand(user.userId(), null, expectedEmailAfterUpdate);

        // when
        handler.handle(command);

        // then
        user = userCommandRepository.getUserByUserId(user.userId());
        assertEquals(user.email(), expectedEmailAfterUpdate);
    }

    @Test
    public void shouldChangeUserUsername() {
        // given
        Username expectedUsernameAfterSave = Username.of("Test2");
        User user = createUser("Test", "test@example.com", RoleCommandMockRepo.TEACHER_ROLE_NAME.value());

        ChangeUserDataCommand command = new ChangeUserDataCommand(user.userId(), expectedUsernameAfterSave, null);

        // when
        handler.handle(command);

        // then
        user = userCommandRepository.getUserByUserId(user.userId());
        assertEquals(user.username(), expectedUsernameAfterSave);
    }

    @Test
    public void shouldChangeUserUsernameAndEmail() {
        // given
        Email expectedEmailAfterUpdate = Email.of("Test2@example.com");
        Username expectedUsernameAfterSave = Username.of("Test2");

        User user = createUser("Test", "test@example.com", RoleCommandMockRepo.TEACHER_ROLE_NAME.value());

        ChangeUserDataCommand command = new ChangeUserDataCommand(user.userId(), expectedUsernameAfterSave, expectedEmailAfterUpdate);

        // when
        handler.handle(command);

        // then
        user = userCommandRepository.getUserByUserId(user.userId());
        assertEquals(user.username(), expectedUsernameAfterSave);
        assertEquals(user.email(), expectedEmailAfterUpdate);
    }
}
