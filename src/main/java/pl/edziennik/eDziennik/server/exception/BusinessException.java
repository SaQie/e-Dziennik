package pl.edziennik.eDziennik.server.exception;

import pl.edziennik.eDziennik.server.basic.dto.ApiValidationResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Business exception that represent exception of business layer
 */
public class BusinessException extends RuntimeException{

    private List<ApiValidationResult> errors = new ArrayList<>();

    public BusinessException(List<ApiValidationResult> errors) {
        super(errors.get(0).getCause());
        this.errors = errors;
    }

    public BusinessException(ApiValidationResult error) {
        super(error.getCause());
        this.errors.add(error);
    }

    public BusinessException(String message) {
        super(message);
    }

    public List<ApiValidationResult> getErrors() {
        return errors;
    }
}
