package pl.edziennik.eDziennik.server.exceptions;

import pl.edziennik.eDziennik.server.basics.dto.ApiErrorDto;

import java.util.ArrayList;
import java.util.List;

public class BusinessException extends RuntimeException{

    private List<ApiErrorDto> errors = new ArrayList<>();

    public BusinessException(List<ApiErrorDto> errors) {
        super(errors.get(0).getCause());
        this.errors = errors;
    }

    public BusinessException(ApiErrorDto error) {
        super(error.getCause());
        this.errors.add(error);
    }

    public BusinessException(String message) {
        super(message);
    }

    public List<ApiErrorDto> getErrors() {
        return errors;
    }
}
