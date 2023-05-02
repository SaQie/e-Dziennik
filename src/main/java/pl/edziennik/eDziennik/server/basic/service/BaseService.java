package pl.edziennik.eDziennik.server.basic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.server.basic.vo.Identifier;
import pl.edziennik.eDziennik.server.exception.EntityNotFoundException;
import pl.edziennik.eDziennik.server.utils.ResourceCreator;

import java.util.function.Supplier;

/**
 * Basics class for service
 */
@Service
public abstract class BaseService {

    @Autowired
    private ResourceCreator resourceCreator;


    /**
     * Returns translated message
     */
    protected String getMessage(String messageKey) {
        return resourceCreator.of(messageKey);
    }

    /**
     * Return translated message with params
     */
    protected String getMessage(String messageKey, Object... objects) {
        return resourceCreator.of(messageKey, objects);
    }

    /**
     * Method used in lambda expression to throw exception when entity was not found
     */
    protected Supplier<EntityNotFoundException> notFoundException(Identifier id, Class clazz) {
        return () -> new EntityNotFoundException(getMessage("not.found.message", id.id(), clazz.getSimpleName()));
    }

    /**
     * Method used in lambda expression to throw exception when entity was not found
     */
    protected Runnable notFoundException(Class clazz, Identifier id) {
        return () -> {
            throw new EntityNotFoundException(getMessage("not.found.message", id.id(), clazz.getSimpleName()));
        };
    }
}
