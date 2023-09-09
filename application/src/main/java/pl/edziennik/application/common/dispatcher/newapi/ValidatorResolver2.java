package pl.edziennik.application.common.dispatcher.newapi;

import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import pl.edziennik.application.common.dispatcher.Command;
import pl.edziennik.application.common.dispatcher.EmptyValidator;
import pl.edziennik.application.common.dispatcher.Handler;
import pl.edziennik.application.common.dispatcher.Validator;
import pl.edziennik.infrastructure.spring.exception.ResolverException;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

@Component
@AllArgsConstructor
public class ValidatorResolver2 {

    public final ApplicationContext applicationContext;

    protected final Validator<? extends Command> resolve(Command command) {
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

        // 7. Check argument types correct
        checkGenericArgumentTypes(beansOfType, command);

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

    private void checkGenericArgumentTypes(Map<String, ? extends Validator<? extends Command>> beansOfType, Command command) {
        Validator<? extends Command> validator = beansOfType.values().iterator().next();
        Type[] typeArgumentsHandler = ((ParameterizedType) validator.getClass().getGenericInterfaces()[0]).getActualTypeArguments();
        Class<?> commandTypeParameter = (Class<?>) typeArgumentsHandler[0];

        if (!commandTypeParameter.equals(command.getClass())) {
            throw new RuntimeException("Class " + validator.getClass().getSimpleName() + " has invalid generic parameter ");
        }
    }

}
