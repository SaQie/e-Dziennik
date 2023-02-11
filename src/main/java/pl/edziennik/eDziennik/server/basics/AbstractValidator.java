package pl.edziennik.eDziennik.server.basics;

import java.util.Optional;

/**
 * Basic interface for validate
 *
 * @param <INPUT>
 */
public interface AbstractValidator<INPUT> {

    String getValidatorName();

    Integer getValidationNumber();

    ValidatorPriority getValidationPriority();

    Optional<ApiErrorsDto> validate(INPUT e);


}
