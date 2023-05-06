package pl.edziennik.eDziennik.infrastructure.spring.exception;

import pl.edziennik.eDziennik.infrastructure.validator.ValidationError;

import java.util.List;

/**
 * Class that represent specific business error
 */
public class BusinessException extends RuntimeException {

    private final List<ValidationError> errors;


    public BusinessException(List<ValidationError> errors) {
        super(String.format("Business exception occurred, list of errors: %s", toStringErrorList(errors)));
        this.errors = errors;
    }

    private static String toStringErrorList(List<ValidationError> errors) {
        StringBuilder sb = new StringBuilder();
        for (ValidationError error : errors) {
            sb.append(error.toString()).append("\n");
        }
        return sb.toString();
    }

    public List<ValidationError> getErrors() {
        return errors;
    }
}
