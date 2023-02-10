package pl.edziennik.eDziennik.server.basics;

import java.util.Optional;

/**
 * Basic interface for {@link Validator} chain
 *
 * @param <T>
 */
public interface AbstractValidator<T>{

    String getValidatorName();
    Integer getValidationNumber();
    ValidatorPriority getValidationPriority();
    Optional<ApiErrorsDto> validate(T e);


}
