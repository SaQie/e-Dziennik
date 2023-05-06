package pl.edziennik.eDziennik.server.basic.validator;

import pl.edziennik.eDziennik.server.basic.dto.ApiValidationResult;

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

    default Optional<ApiValidationResult> validate(INPUT e) {
        return Optional.empty();
    }

    default Optional<ApiValidationResult> validate(INPUT... inputs) {
        return Optional.empty();
    }


}
