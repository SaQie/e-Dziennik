package pl.edziennik.web.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import pl.edziennik.application.common.dispatcher.exception.BusinessException;
import pl.edziennik.common.exception.InvalidIdentifierException;
import pl.edziennik.infrastructure.spring.ResourceCreator;
import pl.edziennik.infrastructure.validator.ValidationError;
import pl.edziennik.infrastructure.validator.errorcode.ErrorCode;

import java.util.List;

@ControllerAdvice
@AllArgsConstructor
public class ExceptionControllerHandler {

    private final ResourceCreator res;

    @ExceptionHandler(value = BusinessException.class)
    protected ResponseEntity<List<ValidationError>> handleBusinessException(BusinessException exception, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getErrors());
    }

    @ExceptionHandler(value = InvalidIdentifierException.class)
    protected ResponseEntity<ValidationError> handleInvalidIdentifierException(InvalidIdentifierException exception,
                                                                               WebRequest webRequest) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ValidationError("Uri identifier", res.of(
                "invalid.identifier"),
                ErrorCode.INVALID_IDENTIFIER.errorCode()));
    }

}
