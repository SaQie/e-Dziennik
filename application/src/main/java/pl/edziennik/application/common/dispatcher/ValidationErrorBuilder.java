package pl.edziennik.application.common.dispatcher;


import pl.edziennik.infrastructure.spring.ResourceCreator;
import pl.edziennik.infrastructure.spring.exception.BusinessException;
import pl.edziennik.infrastructure.validator.ValidationError;
import pl.edziennik.infrastructure.validator.errorcode.ErrorCode;

import java.util.ArrayList;
import java.util.List;

/**
 * Validation result builder for each Command/Query validator
 */
public class ValidationErrorBuilder {

    private static final String NOT_FOUND_MESSAGE_KEY = "not.found.message";
    private final ResourceCreator res;

    public ValidationErrorBuilder(ResourceCreator resourceCreator) {
        this.res = resourceCreator;
    }

    private final List<ValidationError> errors = new ArrayList<>();


    public ValidationErrorBuilder addError(ValidationError error) {
        this.errors.add(error);
        return this;
    }

    public void addError(String field, String message, ErrorCode errorCode, Object... objects) {
        ValidationError validationError = new ValidationError(field, res.of(message, objects), errorCode.errorCode());
        this.errors.add(validationError);
    }

    public void addError(String field, String message, String detail, ErrorCode errorCode, Object... objects) {
        ValidationError validationError = new ValidationError(field, errorCode.errorCode(), res.of(message, objects), detail);
        this.errors.add(validationError);
    }

    /**
     * Adding a not found error and throws the error immediately
     */
    public void addNotFoundError(String field) {
        ValidationError validationError = new ValidationError(field, res.of(NOT_FOUND_MESSAGE_KEY, field),
                ErrorCode.OBJECT_NOT_EXISTS.errorCode());
        this.errors.add(validationError);
    }

    public List<ValidationError> getErrors() {
        return this.errors;
    }

    public void clear() {
        this.errors.clear();
    }

    /**
     * Throw an exception with collection of errors
     */
    public void flush() {
        build();
    }

    public boolean errorExists() {
        return !errors.isEmpty();
    }


    public void build() {
        if (!errors.isEmpty()) {
            throw new BusinessException(errors);
        }
    }


}
