package pl.edziennik.eDziennik.server.basics;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.edziennik.eDziennik.exceptions.BusinessException;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class Validator<T>{

    private final List<AbstractValidator<T>> validators;

    public void validate(T t){
        List<ApiErrorsDto> erros = new ArrayList<>();
        validators.forEach(valid -> valid.validate(t).ifPresent(error -> {
            if (error.isThrownImmediately()){
                throw new BusinessException(error);
            }
            erros.add(error);
        }));
        if (!erros.isEmpty()) {
            throw new BusinessException(erros);
        }
    }

}
