package pl.edziennik.eDziennik.server.basics.validator;

import pl.edziennik.eDziennik.server.basics.dto.ApiErrorDto;

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

    Optional<ApiErrorDto> validate(INPUT e);


}
