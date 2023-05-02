package pl.edziennik.eDziennik.server.exception;

import jakarta.persistence.NoResultException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.edziennik.eDziennik.server.basic.dto.ApiResponse;
import pl.edziennik.eDziennik.server.basic.dto.ApiResponseCreator;
import pl.edziennik.eDziennik.server.basic.dto.ApiValidationResult;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestControllerAdvice
@Slf4j
@SuppressWarnings("rawtypes")
public class ExceptionControllerHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(value = {EntityNotFoundException.class, NoResultException.class})
    protected ResponseEntity<ApiResponse> handleException(RuntimeException exception, WebRequest request) throws URISyntaxException {
        HttpServletRequest httpRequest = ((ServletWebRequest) request).getRequest();
        URI uri = new URI(httpRequest.getRequestURL().toString());
        List<ApiValidationResult> errors = List.of(new ApiValidationResult(null, exception.getMessage(), false, "HANDLER", ExceptionType.VALIDATION));
        log.error("ERROR ON PATH " + request.getDescription(false) + " ERROR MESSAGE: " + exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ApiResponseCreator.buildApiResponse(HttpMethod.valueOf(httpRequest.getMethod()), HttpStatus.NOT_FOUND, uri, errors));
    }

    @ExceptionHandler(value = BusinessException.class)
    protected ResponseEntity<ApiResponse> handleBusinessException(BusinessException exception, WebRequest request) throws URISyntaxException {
        HttpServletRequest httpRequest = ((ServletWebRequest) request).getRequest();
        URI uri = new URI(httpRequest.getRequestURL().toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiResponseCreator.buildApiResponse(HttpMethod.valueOf(httpRequest.getMethod()), HttpStatus.BAD_REQUEST, uri, exception)
        );
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        HttpServletRequest httpRequest = ((ServletWebRequest) request).getRequest();
        BindingResult bindingResult = exception.getBindingResult();
        List<ApiValidationResult> errors = getErrors(bindingResult);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseCreator.buildApiResponse(HttpMethod.valueOf(httpRequest.getMethod()), HttpStatus.BAD_REQUEST, httpRequest.getRequestURL().toString(), errors));
    }


    private static List<ApiValidationResult> getErrors(BindingResult bindingResult) {
        return bindingResult.getFieldErrors()
                .stream()
                .map(error -> new ApiValidationResult(error.getField(), error.getDefaultMessage(), false, MethodArgumentNotValidException.class.getSimpleName(), ExceptionType.VALIDATION))
                .toList();
    }
}
