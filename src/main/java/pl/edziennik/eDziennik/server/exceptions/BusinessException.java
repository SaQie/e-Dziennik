package pl.edziennik.eDziennik.server.exceptions;

import pl.edziennik.eDziennik.server.basics.dto.ApiErrorsDto;

import java.util.ArrayList;
import java.util.List;

public class BusinessException extends RuntimeException{

    private List<ApiErrorsDto> errors = new ArrayList<>();

    public BusinessException(List<ApiErrorsDto> errors) {
        super(errors.get(0).getCause());
        this.errors = errors;
    }

    public BusinessException(ApiErrorsDto error) {
        super(error.getCause());
        this.errors.add(error);
    }

    public BusinessException(String message) {
        super(message);
    }

    public List<ApiErrorsDto> getErrors() {
        return errors;
    }
}
