package pl.edziennik.application.common.dispatcher.newapi;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.Command;
import pl.edziennik.application.common.dispatcher.CommandHandler;
import pl.edziennik.application.common.dispatcher.Validator;
import pl.edziennik.infrastructure.spring.ResourceCreator;

@Component
@AllArgsConstructor
@Slf4j
@SuppressWarnings("all")
public class Resolver {

    private final HandlerResolver2 handlerResolver;
    private final ValidatorResolver2 validatorResolver;
    private final ResourceCreator resourceCreator;

    protected final CommandBundle resolve(Command command) {
        CommandHandler<? extends Command> handler = handlerResolver.resolve(command);
        Validator<? extends Command> validator = validatorResolver.resolve(command);

        return new CommandBundle(handler, validator);
    }

}
