package pl.edziennik.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import pl.edziennik.application.common.dispatcher.exception.BusinessException;
import pl.edziennik.infrastructure.validator.ValidationError;

import java.net.URISyntaxException;
import java.util.List;

@ControllerAdvice
public class ExceptionControllerHandler {

    @ExceptionHandler(value = BusinessException.class)
    protected ResponseEntity<List<ValidationError>> handleBusinessException(BusinessException exception, WebRequest request) throws URISyntaxException {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getErrors());
    }

}
