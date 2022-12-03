package pl.edziennik.eDziennik.server.basics;

import java.util.Optional;

public interface AbstractValidator<T>{

    String getValidatorName();
    Integer getValidationNumber();
    ValidatorPriority getValidationPriority();
    Optional<ApiErrorsDto> validate(T e);


}
