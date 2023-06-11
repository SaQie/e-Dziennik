package pl.edziennik.web.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.edziennik.common.exception.InvalidIdentifierException;
import pl.edziennik.common.exception.InvalidParameterException;
import pl.edziennik.infrastructure.spring.ResourceCreator;
import pl.edziennik.infrastructure.spring.exception.BusinessException;
import pl.edziennik.infrastructure.validator.ValidationError;
import pl.edziennik.infrastructure.validator.errorcode.ErrorCode;

import java.util.List;

@RestControllerAdvice
@AllArgsConstructor
public class ExceptionControllerHandler extends ResponseEntityExceptionHandler {

    private final ResourceCreator res;

    @ExceptionHandler(value = BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected List<ValidationError> handleBusinessException(BusinessException exception, WebRequest request) {
        return exception.getErrors();
    }

    @ExceptionHandler(value = InvalidIdentifierException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ValidationError handleInvalidIdentifierException(InvalidIdentifierException exception,
                                                               WebRequest webRequest) {
        return new ValidationError("Uri identifier", res.of(
                "invalid.identifier"),
                ErrorCode.INVALID_IDENTIFIER.errorCode());
    }

    @ExceptionHandler(value = InvalidParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ValidationError handleInvalidParameterException(InvalidParameterException exception,
                                                              WebRequest webRequest) {
        return new ValidationError("Uri parameter", res.of(
                "invalid.parameter"),
                ErrorCode.INVALID_PARAMETER.errorCode());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        BindingResult bindingResult = exception.getBindingResult();
        return ResponseEntity.badRequest().body(getValidationErrors(bindingResult));
    }

    private List<ValidationError> getValidationErrors(BindingResult bindingResult) {
        return bindingResult.getFieldErrors().stream()
                .map(error -> new ValidationError(
                        convertObjectFieldNameToPlainString(error.getField()),
                        error.getDefaultMessage(),
                        ErrorCode.BASIC_VALIDATION.errorCode()))
                .toList();
    }

    private String convertObjectFieldNameToPlainString(String objectFieldName) {
        int dotIndex = objectFieldName.indexOf(".");
        return dotIndex == -1 ? objectFieldName : objectFieldName.substring(0, dotIndex);
    }


}
