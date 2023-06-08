package pl.edziennik.application.command.user.changepassword;

import org.junit.jupiter.api.Test;
import pl.edziennik.application.BaseUnitTest;
import pl.edziennik.application.mock.repositories.RoleCommandMockRepo;
import pl.edziennik.common.valueobject.Password;
import pl.edziennik.domain.user.User;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChangePasswordHandlerTest extends BaseUnitTest {

    private final ChangePasswordCommandHandler handler;

    public ChangePasswordHandlerTest() {
        this.handler = new ChangePasswordCommandHandler(userCommandRepository, passwordEncoder);
    }

    @Test
    public void shouldChangeUserPassword() {
        // given
        String expectedPasswordAfterUpdate = "TestAfterUpdate";
        User user = createUser("Test", "Test@example.com", RoleCommandMockRepo.TEACHER_ROLE_NAME.value(), "Test123");

        ChangePasswordCommand command = new ChangePasswordCommand(user.getUserId(), Password.of("Test123"), Password.of(expectedPasswordAfterUpdate));

        // when
        handler.handle(command);

        // then
        user = userCommandRepository.getUserByUserId(user.getUserId());
        assertEquals(user.getPassword().value(), expectedPasswordAfterUpdate);
    }
}
