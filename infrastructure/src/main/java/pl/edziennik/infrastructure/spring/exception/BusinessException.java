package pl.edziennik.infrastructure.spring.exception;


import pl.edziennik.infrastructure.validator.ValidationError;

import java.util.ArrayList;
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

    public BusinessException(ValidationError error) {
        super(error.message());
        this.errors = new ArrayList<>();
        this.errors.add(error);
    }

    public BusinessException(ValidationError error, Throwable t) {
        super(error.message(), t);
        this.errors = new ArrayList<>();
        this.errors.add(error);
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
