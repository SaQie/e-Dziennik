package pl.edziennik.eDziennik.infrastructure.spring.base;

import pl.edziennik.eDziennik.infrastructure.spring.dispatcher.ValidationErrorBuilder;

/**
 * Base validator for all types of {@link IDispatchable} (Query/command)
 *
 * @param <T> -> Object that implements ICommand/IQuery
 */
public interface IBaseValidator<T extends IDispatchable<?>>{

    void validate(T command, ValidationErrorBuilder errorBuilder);
}
