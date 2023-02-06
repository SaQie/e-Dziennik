package pl.edziennik.eDziennik.server.basics;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.edziennik.eDziennik.exceptions.BusinessException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;


@Component
class Validator<T> {

    private List<AbstractValidator<T>> validators;

    protected void setValidators(List<AbstractValidator<T>> validators) {
        this.validators = validators;
    }

    protected boolean isValidatorsDefined() {
        return validators != null && !validators.isEmpty();
    }

    protected void validate(T t) {
        List<ApiErrorsDto> errors = new ArrayList<>();
        validators.forEach(valid -> valid.validate(t).ifPresent(error -> {
            if (error.isThrownImmediately()) {
                throw new BusinessException(error);
            }
            errors.add(error);
        }));
        if (!errors.isEmpty()) {
            throw new BusinessException(errors);
        }

    }

    protected void validateByPriority(T t) {
        List<ApiErrorsDto> errors = new ArrayList<>();
        validators.stream().sorted(Comparator.comparing(validator -> validator.getValidationPriority().ordinal())).
                forEach(valid -> valid.validate(t).ifPresent(error -> {
                    if (error.isThrownImmediately()) {
                        throw new BusinessException(error);
                    }
                    errors.add(error);
                }));
        if (!errors.isEmpty()) {
            throw new BusinessException(errors);
        }

    }

    protected void validateByIds(T t) {
        List<ApiErrorsDto> erros = new ArrayList<>();
        validators.stream().sorted(Comparator.comparing(AbstractValidator::getValidationNumber)).
                forEach(valid -> valid.validate(t).ifPresent(error -> {
                    if (error.isThrownImmediately()) {
                        throw new BusinessException(error);
                    }
                    erros.add(error);
                }));
        if (!erros.isEmpty()) {
            throw new BusinessException(erros);
        }

    }

    protected void validateBySelectedPriority(T t, ValidatorPriority priority) {
        List<ApiErrorsDto> erros = new ArrayList<>();
        validators.stream().filter(validator -> validator.getValidationPriority().equals(priority)).
                forEach(valid -> valid.validate(t).ifPresent(error -> {
                    if (error.isThrownImmediately()) {
                        throw new BusinessException(error);
                    }
                    erros.add(error);
                }));
        if (!erros.isEmpty()) {
            throw new BusinessException(erros);
        }

    }

    protected void runSelectedValidator(T t, Integer validatorId) {
        if (validators != null && !validators.isEmpty()) {
            validators.stream().filter(item -> Objects.equals(item.getValidationNumber(), validatorId))
                    .findFirst().ifPresent(validator -> validator.validate(t).ifPresent(error -> {
                        throw new BusinessException(error);
                    }));
        }
    }

}
