package pl.edziennik.eDziennik.server.basics;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.eDziennik.exceptions.BusinessException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Component
@AllArgsConstructor
public class Validator<T> {

    private final List<AbstractValidator<T>> validators;

    public void validate(T t) {
        if (validators != null && !validators.isEmpty()) {
            List<ApiErrorsDto> erros = new ArrayList<>();
            validators.forEach(valid -> valid.validate(t).ifPresent(error -> {
                if (error.isThrownImmediately()) {
                    throw new BusinessException(error);
                }
                erros.add(error);
            }));
            if (!erros.isEmpty()) {
                throw new BusinessException(erros);
            }
        }
    }

    public void validateByPriority(T t) {
        if (validators != null && !validators.isEmpty()) {
            List<ApiErrorsDto> erros = new ArrayList<>();
            validators.stream().sorted(Comparator.comparing(validator -> validator.getValidationPriority().ordinal())).
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
    }

    public void validateByIds(T t){
        if (validators != null && !validators.isEmpty()) {
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
    }

    public void validateBySelectedPriority(T t, ValidatorPriority priority) {
        if (validators != null && !validators.isEmpty()) {
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
    }

    public void runSelectedValidator(T t, Integer validatorId) {
        if (validators != null && !validators.isEmpty()) {
            validators.stream().filter(item -> Objects.equals(item.getValidationNumber(), validatorId))
                    .findFirst().ifPresent(validator -> validator.validate(t).ifPresent(error -> {
                        throw new BusinessException(error);
                    }));
        }
    }

}
