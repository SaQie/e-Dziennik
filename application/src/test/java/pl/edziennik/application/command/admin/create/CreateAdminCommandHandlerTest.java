package pl.edziennik.application.command.admin.create;

import org.junit.jupiter.api.Test;
import pl.edziennik.application.BaseUnitTest;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.common.valueobject.Email;
import pl.edziennik.common.valueobject.Password;
import pl.edziennik.common.valueobject.Pesel;
import pl.edziennik.common.valueobject.Username;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class CreateAdminCommandHandlerTest extends BaseUnitTest {

    private final CreateAdminCommandHandler handler;

    public CreateAdminCommandHandlerTest() {
        this.handler = new CreateAdminCommandHandler(adminCommandRepository, passwordEncoder, roleCommandRepository);
    }

    @Test
    public void shouldCreateAdmin() {
        // given
        CreateAdminCommand command = new CreateAdminCommand(
                Username.of("Kamil"),
                Email.of("kamcio@o2.pl"),
                Password.of("Test123"),
                Pesel.of("12312312311")
        );

        // when
        OperationResult operationResult = handler.handle(command);

        // then
        assertNotNull(operationResult.identifier());
    }

}