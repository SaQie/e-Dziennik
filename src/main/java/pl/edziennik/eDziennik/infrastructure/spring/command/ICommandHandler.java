package pl.edziennik.eDziennik.infrastructure.spring.command;

import pl.edziennik.eDziennik.infrastructure.spring.base.IBaseHandler;
import pl.edziennik.eDziennik.infrastructure.spring.base.IDispatchable;

/**
 * Implement this interface for all Command Handlers in this system
 *
 * @param <T> Object that implements IQuery/ICommand interface
 * @param <R> Result object
 */
public interface ICommandHandler<T extends IDispatchable<R>, R> extends IBaseHandler<T, R> {
}
