package pl.edziennik.eDziennik.infrastructure.spring.query;

import pl.edziennik.eDziennik.infrastructure.spring.base.IBaseHandler;
import pl.edziennik.eDziennik.infrastructure.spring.base.IDispatchable;

/**
 * Implement this interface for all Query Handlers in this system
 *
 * @param <T> Object that implements IQuery/ICommand interface
 * @param <R> Result object
 */
public interface IQueryHandler<T extends IDispatchable<R>, R> extends IBaseHandler<T, R> {
}
