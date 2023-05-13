package pl.edziennik.application.common.dispatcher.base;


import pl.edziennik.application.common.dispatcher.ValidationErrorBuilder;

/**
 * Base validator for all types of {@link IDispatchable} (Query/command)
 *
 * @param <T> -> Object that implements ICommand/IQuery
 */
public interface IBaseValidator<T extends IDispatchable<?>>{

    void validate(T command, ValidationErrorBuilder errorBuilder);
}