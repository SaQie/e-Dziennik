package pl.edziennik.eDziennik.infrastructure.spring.dispatcher;

import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import pl.edziennik.eDziennik.infrastructure.spring.base.HandledBy;
import pl.edziennik.eDziennik.infrastructure.spring.base.IBaseHandler;
import pl.edziennik.eDziennik.infrastructure.spring.base.IDispatchable;
import pl.edziennik.eDziennik.infrastructure.spring.exception.ResolverException;

import java.util.Map;

@Component
@AllArgsConstructor
@SuppressWarnings("all")
class HandlerResolver {

    private final ApplicationContext context;

    protected <T> IBaseHandler<IDispatchable<T>, T> resolve(final IDispatchable<T> dispatchable) {
        // get annotation above the command/query
        HandledBy handlerAnnotation = dispatchable.getClass().getAnnotation(HandledBy.class);
        if (handlerAnnotation == null) {
            // Handler is required
            throw new RuntimeException("No HandledBy annotation provided for class " + dispatchable.getClass().getSimpleName());
        }
        // Get class, that is annotated
        Class<? extends IBaseHandler> handlerType = handlerAnnotation.handler();

        // get from application context map, that key is bean name, and value is list of instances for that bean
        Map<String, ? extends IBaseHandler> handlers = context.getBeansOfType(handlerType);

        if (handlers.isEmpty()) {
            // If HandledBy annotation is provided, but bean was not found
            throw new ResolverException("Class " + dispatchable.getClass().getSimpleName() + " has defined @HandledBy " +
                    "annotation, but provided handler not found (Make sure that handler is a spring bean)");
        }

        if (handlers.size() > 1) {
            // If there is more than one handler
            throw new ResolverException("Class " + dispatchable.getClass().getSimpleName() + " has more than one defined handler");
        }

        // return one specific handler to execute
        return handlers.values().iterator().next();
    }

}
