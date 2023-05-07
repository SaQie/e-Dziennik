package pl.edziennik.application.common.dispatcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edziennik.application.common.dispatcher.base.IDispatchable;

@Service
public abstract class DispatcherResolver{

    @Autowired
    private Dispatcher dispatcher;

    protected final <T> T callHandler(final IDispatchable<T> dispatchable) {
        return dispatcher.callHandler(dispatchable);
    }

}
