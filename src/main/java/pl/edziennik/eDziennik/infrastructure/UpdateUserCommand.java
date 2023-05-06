package pl.edziennik.eDziennik.infrastructure;

import lombok.Data;
import pl.edziennik.eDziennik.infrastructure.spring.base.HandledBy;
import pl.edziennik.eDziennik.infrastructure.spring.base.ValidatedBy;
import pl.edziennik.eDziennik.infrastructure.spring.command.ICommand;
import pl.edziennik.eDziennik.server.basic.vo.Identifier;

@Data
@HandledBy(handler = UpdateUserCommandHandler.class)
@ValidatedBy(validator = UpdateUserCommandValidator.class)
public class UpdateUserCommand implements ICommand<Identifier> {

    private String dupa;

}
