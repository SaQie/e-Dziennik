package pl.edziennik.application.command.parent.create;

import org.junit.jupiter.api.Test;
import pl.edziennik.application.BaseUnitTest;
import pl.edziennik.application.common.dispatcher.OperationResult;
import pl.edziennik.common.valueobject.*;
import pl.edziennik.common.valueobject.id.ParentId;
import pl.edziennik.domain.parent.Parent;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class CreateParentCommandHandlerTest extends BaseUnitTest {

    private final CreateParentCommandHandler handler;

    public CreateParentCommandHandlerTest() {
        this.handler = new CreateParentCommandHandler(parentCommandRepository, passwordEncoder, roleCommandRepository, publisher);
    }


    @Test
    public void shouldCreateParent() {
        // given
        CreateParentCommand command = new CreateParentCommand(
                Password.of("Test"),
                Username.of("Kamil"),
                FirstName.of("Nowak"),
                LastName.of("Test"),
                Address.of("TEST"),
                PostalCode.of("55-200"),
                City.of("Daleko"),
                Pesel.of("123123123"),
                Email.of("Kamcio@o2.pl"),
                PhoneNumber.of("999999999"),
                null
        );

        // when
        OperationResult operationResult = handler.handle(command);

        // then
        assertNotNull(operationResult.identifier());
        Parent parent = parentCommandRepository.getReferenceById(ParentId.of(operationResult.identifier().id()));
        assertNotNull(parent);
    }

}