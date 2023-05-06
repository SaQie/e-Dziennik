package pl.edziennik.eDziennik.infrastructure;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.domain.user.repository.UserRepository;
import pl.edziennik.eDziennik.infrastructure.spring.command.ICommandHandler;
import pl.edziennik.eDziennik.server.basic.vo.Identifier;

@Service
@AllArgsConstructor
public class CreateUserCommandHandler implements ICommandHandler<CreateUserCommand, Identifier> {

    private final UserRepository userRepository;

    @Override
    public Identifier handle(CreateUserCommand command) {
        System.out.println("Dziala");
        return null;
    }
}
