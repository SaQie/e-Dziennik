package pl.edziennik.eDziennik.server.basics.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.server.basics.validator.BasicValidator;
import pl.edziennik.eDziennik.server.utils.ResourceCreator;

/**
 * Basics class for service
 */
@Service
public abstract class BaseService {

    @Autowired
    private ResourceCreator resourceCreator;

    @Autowired
    public BasicValidator basicValidator;

    /**
     * Returns translated message
     */
    protected String getMessage(String messageKey){
        return resourceCreator.of(messageKey);
    }

    /**
     * Return translated message with params
     */
    protected String getMessage(String messageKey, Object... objects){
        return resourceCreator.of(messageKey, objects);
    }
}
