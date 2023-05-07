package pl.edziennik.application.common.dispatcher.command;


import pl.edziennik.application.common.dispatcher.base.IDispatchable;

/**
 * Implement this interface for all Command in system
 *
 * Make sure that Query class has @HandledBy annotation
 *
 * @param <T> -> Response object
 */
public interface ICommand<T> extends IDispatchable<T> {
}
