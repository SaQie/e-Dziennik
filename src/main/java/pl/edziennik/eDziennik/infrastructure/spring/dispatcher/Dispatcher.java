package pl.edziennik.eDziennik.infrastructure.spring.dispatcher;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.edziennik.eDziennik.infrastructure.spring.base.IBaseHandler;
import pl.edziennik.eDziennik.infrastructure.spring.base.IDispatchable;

/**
 * Component that execute and validate concrete Query/Command handlers/validators for specific ICommand,IQuery instances
 */
@Component
@AllArgsConstructor
@Slf4j
public class Dispatcher {

    private final HandlerResolver handlerResolver;
    private final ValidatorResolver validatorResolver;

    public <T> T callHandler(final IDispatchable<T> dispatchable) {
        IBaseHandler<IDispatchable<T>, T> handler = handlerResolver.resolve(dispatchable);

        validatorResolver.resolve(dispatchable)
                .ifPresent(validator -> {
                    log.info("Executing validator " + validator.getClass().getSimpleName());
                    ValidationErrorBuilder validationErrorBuilder = new ValidationErrorBuilder();
                    validator.validate(dispatchable, validationErrorBuilder);
                    if (validationErrorBuilder.errorExists()) {
                        log.error("Error during validate " + dispatchable.getClass().getSimpleName());
                    }
                    validationErrorBuilder.build();

                });

        log.info("Executing " + dispatchable.getClass().getSimpleName() + " via handler " + handler.getClass().getSimpleName());
        return handler.handle(dispatchable);
    }

}
