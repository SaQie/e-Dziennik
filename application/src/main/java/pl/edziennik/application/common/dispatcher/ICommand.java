package pl.edziennik.application.common.dispatcher;


/**
 * Implement this interface for all Command in system
 *
 * Make sure that Query class has @HandledBy annotation
 *
 * @param <T> -> Response object
 */
public interface ICommand<T extends OperationResult> extends IDispatchable<T> {
}
