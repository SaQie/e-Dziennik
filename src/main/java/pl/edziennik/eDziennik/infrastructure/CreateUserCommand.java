package pl.edziennik.eDziennik.infrastructure;

import lombok.Data;
import pl.edziennik.eDziennik.infrastructure.spring.base.HandledBy;
import pl.edziennik.eDziennik.infrastructure.spring.command.ICommand;
import pl.edziennik.eDziennik.server.basic.vo.Identifier;

@HandledBy(handler = CreateUserCommandHandler.class)
@Data
public class CreateUserCommand implements ICommand<Identifier> {

    private String name;
    private String lastName;

}
