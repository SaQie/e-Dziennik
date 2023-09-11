package pl.edziennik.application.common.dispatcher.newapi;

import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.Command;
import pl.edziennik.application.common.dispatcher.CommandHandler;
import pl.edziennik.application.common.dispatcher.Handler;
import pl.edziennik.infrastructure.spring.exception.ResolverException;

import java.util.Map;

@Component
@AllArgsConstructor
public class HandlerResolver2 {

    public final ApplicationContext applicationContext;

    protected final CommandHandler<? extends Command> resolve(Command command) {
        // 1.Get annotation above command
        Handler handler = command.getClass().getAnnotation(Handler.class);

        // 2. Check annotation exists
        requireHandler(handler, command);

        // 3. Get command handler
        Class<? extends CommandHandler<? extends Command>> commandHandler = handler.handler();

        // 4. Get beans of type
        Map<String, ? extends CommandHandler<? extends Command>> beansOfType = applicationContext.getBeansOfType(commandHandler);

        // 5. Check command handler found
        checkBeansMapIsEmpty(beansOfType, command);

        // 6. Check command handlers size
        checkMoreThanOneHandlerExists(beansOfType, command);

        return beansOfType.values().iterator().next();

    }

    private static void checkMoreThanOneHandlerExists(Map<String, ? extends CommandHandler<? extends Command>> beansOfType, Command command) {
        if (beansOfType.size() > 1) {
            // If there is more than one handler
            throw new ResolverException("Class " + command.getClass().getSimpleName() + " has more than one defined handler");
        }
    }

    private void checkBeansMapIsEmpty(Map<String, ? extends CommandHandler<? extends Command>> beansOfType, Command command) {
        if (beansOfType.isEmpty()) {
            // If Handler annotation is provided, but bean was not found
            throw new ResolverException("Class " + command.getClass().getSimpleName() + " has defined @Handler " +
                    "annotation, but provided handler not found (Make sure that handler is a spring bean)");
        }
    }

    private void requireHandler(Handler handler, Command command) {
        if (handler == null) {
            // Handler is required
            throw new RuntimeException("No Handler annotation provided for class " + command.getClass().getSimpleName());
        }
    }

}
