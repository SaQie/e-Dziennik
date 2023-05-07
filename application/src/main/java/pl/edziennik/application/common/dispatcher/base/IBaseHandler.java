package pl.edziennik.application.common.dispatcher.base;

/**
 * Base handler for all types of {@link IDispatchable} (Query/Command)
 *
 * @param <T> -> Object that implements ICommand/IQuery interface
 * @param <R> -> Result object
 */
public interface IBaseHandler<T extends IDispatchable<R>, R> {

    R handle(final T command);

}
