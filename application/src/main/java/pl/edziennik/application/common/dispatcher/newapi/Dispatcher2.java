package pl.edziennik.application.common.dispatcher.newapi;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import pl.edziennik.application.common.dispatcher.Command;
import pl.edziennik.application.common.dispatcher.CommandHandler;
import pl.edziennik.application.common.dispatcher.ValidationErrorBuilder;
import pl.edziennik.application.common.dispatcher.Validator;
import pl.edziennik.infrastructure.spring.ResourceCreator;

@Component
@AllArgsConstructor
@Slf4j
@SuppressWarnings("all")
public class Dispatcher2 {

    private final ResourceCreator resourceCreator;
    private final Resolver resolver;

    public final void run(final Command command) {
        CommandBundle commandBundle = resolver.resolve(command);

        if (commandBundle.validator() != null) {
            runValidator(command, commandBundle.validator());
        }

        runHandler(command, commandBundle.handler());

    }

    private <T extends Command> void runValidator(T command, Validator<Command> validator) {
        if (log.isDebugEnabled()) {
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();

            executeValidator(command, validator);

            stopWatch.stop();
            log.debug("Validator {} took {} ms to execute ", validator.getClass().getSimpleName(),
                    stopWatch.getTotalTimeMillis());
            return;
        }

        executeValidator(command, validator);
    }

    private <T extends Command> void executeValidator(T command, Validator<T> validator) {
        ValidationErrorBuilder errorBuilder = new ValidationErrorBuilder(resourceCreator);
        validator.validate(command, errorBuilder);
        if (errorBuilder.errorExists()) {
            errorBuilder.build();
        }

    }

    private void runHandler(Command command, CommandHandler<Command> handler) {
        if (log.isDebugEnabled()) {
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();

            handler.handle(command);

            stopWatch.stop();
            log.debug("Handler {} took {} ms to execute ", handler.getClass().getSimpleName(),
                    stopWatch.getTotalTimeMillis());
            return;
        }
        handler.handle(command);
    }

}
