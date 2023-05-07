package pl.edziennik.application.common.dispatcher.query;


import pl.edziennik.application.common.dispatcher.base.IBaseHandler;
import pl.edziennik.application.common.dispatcher.base.IDispatchable;

/**
 * Implement this interface for all Query Handlers in this system
 *
 * @param <T> Object that implements IQuery/ICommand interface
 * @param <R> Result object
 */
public interface IQueryHandler<T extends IDispatchable<R>, R> extends IBaseHandler<T, R> {
}
