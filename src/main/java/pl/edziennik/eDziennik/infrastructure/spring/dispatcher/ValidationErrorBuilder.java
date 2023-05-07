package pl.edziennik.eDziennik.infrastructure.spring.dispatcher;

import pl.edziennik.eDziennik.infrastructure.spring.exception.BusinessException;
import pl.edziennik.eDziennik.infrastructure.validator.ValidationError;
import pl.edziennik.eDziennik.infrastructure.validator.errorcode.ErrorCode;

import java.util.ArrayList;
import java.util.List;

/**
 * Validation result builder for each Command/Query validator
 */
public class ValidationErrorBuilder {

    ValidationErrorBuilder(){

    }

    private final List<ValidationError> errors = new ArrayList<>();

    public ValidationErrorBuilder addError(ValidationError error){
        this.errors.add(error);
        return this;
    }

    public ValidationErrorBuilder addError(String field, String message, ErrorCode errorCode){
        ValidationError validationError = new ValidationError(field, message, errorCode.errorCode());
        this.errors.add(validationError);
        return this;
    }

    public boolean errorExists(){
        return !errors.isEmpty();
    }


    protected void build(){
        if (!errors.isEmpty()){
            throw new BusinessException(errors);
        }
    }



}
