package pl.edziennik.application.command.admin.create;

import org.junit.jupiter.api.Test;
import pl.edziennik.application.BaseUnitTest;
import pl.edziennik.common.valueobject.vo.Email;
import pl.edziennik.common.valueobject.vo.Password;
import pl.edziennik.common.valueobject.vo.Pesel;
import pl.edziennik.common.valueobject.vo.Username;
import pl.edziennik.domain.admin.Admin;
import pl.edziennik.domain.user.User;
import pl.edziennik.infrastructure.validator.ValidationError;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CreateAdminCommandValidatorTest extends BaseUnitTest {

    private final CreateAdminCommandValidator validator;

    public CreateAdminCommandValidatorTest() {
        this.validator = new CreateAdminCommandValidator(adminCommandRepository);
    }

    @Test
    public void shouldAddErrorWhenAdminAlreadyExists() {
        // given
        User user = createUser("Test", "test@o2.pl", "ADMIN");
        Admin admin = createAdmin(user);
        adminCommandRepository.save(admin);

        CreateAdminCommand command = new CreateAdminCommand(
                Username.of("Test"),
                Email.of("Test@o2.pl"),
                Password.of("password"),
                Pesel.of("12312312311")
        );

        // when
        validator.validate(command, validationErrorBuilder);

        // then
        List<ValidationError> errors = validationErrorBuilder.getErrors();
        assertEquals(errors.size(), 1);
        assertEquals(errors.get(0).field(), CreateAdminCommand.USERNAME);
        assertEquals(errors.get(0).message(), CreateAdminCommandValidator.MESSAGE_KEY_ADMIN_ALREADY_EXISTS);
    }
}
