package pl.edziennik.eDziennik.infrastructure.spring.dispatcher;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import pl.edziennik.eDziennik.infrastructure.spring.base.IBaseHandler;
import pl.edziennik.eDziennik.infrastructure.spring.base.IDispatchable;

/**
 * Component that execute and validate concrete Query/Command handlers/validators for specific ICommand,IQuery instances
 */
@Component
@AllArgsConstructor
public class Dispatcher {

    private final HandlerResolver handlerResolver;
    private final ValidatorResolver validatorResolver;
    private final Logger logger;

    public <T> T callHandler(final IDispatchable<T> dispatchable) {
        IBaseHandler<IDispatchable<T>, T> handler = handlerResolver.resolve(dispatchable);

        validatorResolver.resolve(dispatchable)
                .ifPresent(validator -> {
                    ValidationResultBuilder validationResultBuilder = new ValidationResultBuilder();
                    validator.validate(dispatchable, validationResultBuilder);
                    if (validationResultBuilder.errorExists()) {
                        logger.error("Error during validate " + dispatchable.getClass().getSimpleName());
                    }
                    validationResultBuilder.build();

                });

        logger.info("Executing " + dispatchable.getClass().getSimpleName() + " via handler " + handler.getClass().getSimpleName());
        return handler.handle(dispatchable);
    }

}
