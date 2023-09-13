package pl.edziennik.application.common.dispatcher;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.edziennik.infrastructure.spring.ResourceCreator;

@Component
@AllArgsConstructor
@Slf4j
@SuppressWarnings("all")
final class Resolver {

    private final HandlerResolver handlerResolver;
    private final ValidatorResolver validatorResolver;
    private final ResourceCreator resourceCreator;

    CommandBundle resolve(Command command) {
        CommandHandler<? extends Command> handler = handlerResolver.resolve(command);
        Validator<? extends Command> validator = validatorResolver.resolve(command);

        return new CommandBundle(handler, validator);
    }

}
