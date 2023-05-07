package pl.edziennik.application.command.user.createuser;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import pl.edziennik.application.command.student.service.create.CreateStudentCommand;
import pl.edziennik.application.common.dispatcher.base.HandledBy;
import pl.edziennik.application.common.dispatcher.base.ValidatedBy;
import pl.edziennik.application.common.dispatcher.command.ICommand;
import pl.edziennik.domain.role.Role;
import pl.edziennik.domain.user.UserId;

@Getter
@AllArgsConstructor
@Accessors(fluent = true)
@HandledBy(handler = CreateUserCommandHandler.class)
@ValidatedBy(validator = CreateUserCommandValidator.class)
public class CreateUserCommand implements ICommand<UserId> {

    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String EMAIL = "email";
    public static final String ROLE = "role";

    private final String username;
    private final String password;
    private final String email;
    private final String role;


    public static CreateUserCommand fromStudent(CreateStudentCommand command) {
        return new CreateUserCommand(command.username(), command.password(), command.email(), Role.RoleConst.ROLE_STUDENT.name());
    }

}
