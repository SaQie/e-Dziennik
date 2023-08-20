package pl.edziennik.application.command.admin.create;

import org.junit.jupiter.api.Test;
import pl.edziennik.application.BaseUnitTest;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.common.valueobject.vo.Email;
import pl.edziennik.common.valueobject.vo.Password;
import pl.edziennik.common.valueobject.vo.Pesel;
import pl.edziennik.common.valueobject.vo.Username;

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