package pl.edziennik.eDziennik.server.basics.validator;

import pl.edziennik.eDziennik.server.basics.dto.ApiErrorDto;

import java.util.Optional;
import java.util.Set;

/**
 * Basic interface for validate
 *
 * @param <INPUT>
 */
public interface AbstractValidator<INPUT> {

    String getValidatorId();

    /**
     * Override this method if needed to define more specific validate purpose
     *
     * @return
     */
    default Set<ValidatePurpose> getValidatorPurposes() {
        return Set.of(ValidatePurpose.CREATE);
    }

    default String getValidatorName(Class clazz) {
        return clazz.getSimpleName().substring(0, 1).toLowerCase()
                + clazz.getSimpleName().substring(1);
    }

    Optional<ApiErrorDto> validate(INPUT e);


}
