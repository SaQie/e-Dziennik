package pl.edziennik.application.common.dispatcher;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import pl.edziennik.infrastructure.spring.ResourceCreator;

/**
 * Component that execute and validate concrete Query/Command handlers/validators for specific ICommand,IQuery instances
 */
@Component
@AllArgsConstructor
@Slf4j
public class Dispatcher {

    private final HandlerResolver handlerResolver;
    private final ValidatorResolver validatorResolver;
    private final ResourceCreator resourceCreator;

    public final <T> T dispatch(final IDispatchable<T> dispatchable) {
        IBaseHandler<IDispatchable<T>, T> handler = handlerResolver.resolve(dispatchable);

        executeValidators(dispatchable);

        log.info("Executing " + dispatchable.getClass().getSimpleName() + " via handler " + handler.getClass().getSimpleName());
        return executeHandler(dispatchable, handler);
    }

    private <T> T executeHandler(IDispatchable<T> dispatchable, IBaseHandler<IDispatchable<T>, T> handler) {
        if (log.isDebugEnabled()) {
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();

            T handlerResult = handler.handle(dispatchable);
            stopWatch.stop();

            log.debug("Dispatchable {} took {} ms to execute handler", dispatchable.getClass().getSimpleName(),
                    stopWatch.getTotalTimeMillis());
            return handlerResult;
        }
        return handler.handle(dispatchable);
    }


    private <T> void executeValidators(IDispatchable<T> dispatchable) {
        if (log.isDebugEnabled()) {
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();

            runValidators(dispatchable);
            stopWatch.stop();

            log.debug("Dispatchable {} took {} ms to execute validators", dispatchable.getClass().getSimpleName(),
                    stopWatch.getTotalTimeMillis());
        } else {
            runValidators(dispatchable);
        }
    }

    private <T> void runValidators(IDispatchable<T> dispatchable) {
        validatorResolver.resolve(dispatchable)
                .ifPresent(validator -> {
                    log.info("Executing validator " + validator.getClass().getSimpleName());
                    ValidationErrorBuilder validationErrorBuilder = new ValidationErrorBuilder(resourceCreator);
                    validator.validate(dispatchable, validationErrorBuilder);
                    if (validationErrorBuilder.errorExists()) {
                        log.error("Error during validate " + dispatchable.getClass().getSimpleName());
                    }
                    validationErrorBuilder.build();
                });
    }

}
