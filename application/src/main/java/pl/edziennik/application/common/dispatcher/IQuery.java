package pl.edziennik.application.common.dispatcher;


/**
 * Implement this interface for all Query in this system
 *
 * Make sure that Query class has @HandledBy annotation
 *
 * @param <T> -> Response object
 */
public interface IQuery<T> extends IDispatchable<T> {
}
