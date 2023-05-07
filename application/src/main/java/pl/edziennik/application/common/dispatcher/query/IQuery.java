package pl.edziennik.application.common.dispatcher.query;


import pl.edziennik.application.common.dispatcher.base.IDispatchable;

/**
 * Implement this interface for all Query in this system
 *
 * Make sure that Query class has @HandledBy annotation
 *
 * @param <T> -> Response object
 */
public interface IQuery<T> extends IDispatchable<T> {
}
