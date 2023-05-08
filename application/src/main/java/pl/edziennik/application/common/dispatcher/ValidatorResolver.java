package pl.edziennik.application.common.dispatcher;

import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.base.IBaseValidator;
import pl.edziennik.application.common.dispatcher.base.IDispatchable;
import pl.edziennik.application.common.dispatcher.base.ValidatedBy;
import pl.edziennik.application.common.dispatcher.exception.ResolverException;

import java.util.Map;
import java.util.Optional;

@Component
@AllArgsConstructor
@SuppressWarnings("all")
class ValidatorResolver {

    private final ApplicationContext context;

    protected final <T> Optional<IBaseValidator<IDispatchable<T>>> resolve(IDispatchable<T> dispatchable) {
        // get annotation above the command/query
        ValidatedBy validatedBy = dispatchable.getClass().getAnnotation(ValidatedBy.class);
        if (validatedBy == null) {
            // If validatedBy is not provided
            return Optional.empty();
        }
        // Get class, that is annotated
        Class<? extends IBaseValidator> validator = validatedBy.validator();

        // get from application context map, that key is bean name, and value is list of instances for that bean
        Map<String, ? extends IBaseValidator> validators = context.getBeansOfType(validator);

        if (validators.isEmpty()) {
            // If ValidatedBy annotation is provided, but bean was not found
            throw new ResolverException("Class " + dispatchable.getClass().getSimpleName() + " has defined @ValidatedBy " +
                    "annotation, but provided validator not found (Make sure that validator is a spring bean)");
        }

        if (validators.size() > 1) {
            // If there is more than one validator
            throw new ResolverException("Class " + dispatchable.getClass().getSimpleName() + " has defined more than one validator");
        }

        // return one specific validator to execute
        return Optional.of(validators.values().iterator().next());
    }
}
