package pl.edziennik.eDziennik.command.user.createuser;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import pl.edziennik.eDziennik.command.student.service.create.CreateStudentCommand;
import pl.edziennik.eDziennik.domain.role.domain.Role;
import pl.edziennik.eDziennik.domain.user.domain.wrapper.UserId;
import pl.edziennik.eDziennik.infrastructure.spring.base.HandledBy;
import pl.edziennik.eDziennik.infrastructure.spring.base.ValidatedBy;
import pl.edziennik.eDziennik.infrastructure.spring.command.ICommand;

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
