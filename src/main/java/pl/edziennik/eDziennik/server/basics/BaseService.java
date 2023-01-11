package pl.edziennik.eDziennik.server.basics;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edziennik.eDziennik.server.utils.ResourceCreator;

@Service
public abstract class BaseService {

    @Autowired
    private ResourceCreator resourceCreator;

    @Autowired
    public BasicValidator basicValidator;

    protected String getMessage(String messageKey){
        return resourceCreator.of(messageKey);
    }

    protected String getMessage(String messageKey, Object... objects){
        return resourceCreator.of(messageKey, objects);
    }
}
