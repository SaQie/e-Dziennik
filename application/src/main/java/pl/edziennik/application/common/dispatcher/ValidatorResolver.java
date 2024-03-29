package pl.edziennik.application.common.dispatcher;

import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import pl.edziennik.infrastructure.spring.exception.ResolverException;

import java.util.Map;

/**
 * Class responsible for getting proper validator
 */
@Component
@AllArgsConstructor
final class ValidatorResolver {

    public final ApplicationContext applicationContext;

    Validator<? extends Command> resolve(Command command) {
        // 1. Get annotation above command
        Handler handler = command.getClass().getAnnotation(Handler.class);

        // 2. Get validator from annotation
        Class<? extends Validator<? extends Command>> validator = handler.validator();

        // 3. If only default validator provided
        if (validator.equals(EmptyValidator.class)) {
            return null;
        }

        // 4. Get beans of type
        Map<String, ? extends Validator<? extends Command>> beansOfType = applicationContext.getBeansOfType(validator);

        // 5. Check validator is a spring bean
        checkValidatorFound(beansOfType, command);

        // 6. Check more than one validator defined
        checkMoreThanOneValidatorExists(beansOfType, command);

        return beansOfType.values().iterator().next();
    }

    private void checkValidatorFound(Map<String, ? extends Validator<? extends Command>> beansOfType, Command command) {
        if (beansOfType.isEmpty()) {
            throw new ResolverException("Class " + command.getClass().getSimpleName() + " has provided validator, but provided validator not found (Make sure that validator is a spring bean)");
        }
    }

    private void checkMoreThanOneValidatorExists(Map<String, ? extends Validator<? extends Command>> beansOfType, Command command) {
        if (beansOfType.size() > 1) {
            throw new ResolverException("Class " + command.getClass().getSimpleName() + " has defined more than one validator");
        }
    }

}
