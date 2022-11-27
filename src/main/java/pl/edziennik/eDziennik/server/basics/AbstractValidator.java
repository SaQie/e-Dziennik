package pl.edziennik.eDziennik.server.basics;

import java.util.Optional;

public interface AbstractValidator<T>{

    Integer getValidationNumber();
    ValidatorPriority getValidationPriority();
    Optional<ApiErrorsDto> validate(T t);


}
