package pl.edziennik.application.common.dispatcher.command;


import pl.edziennik.application.common.dispatcher.base.IBaseHandler;
import pl.edziennik.application.common.dispatcher.base.IDispatchable;

/**
 * Implement this interface for all Command Handlers in this system
 *
 * @param <T> Object that implements IQuery/ICommand interface
 * @param <R> Result object
 */
public interface ICommandHandler<T extends IDispatchable<R>, R> extends IBaseHandler<T, R> {
}
