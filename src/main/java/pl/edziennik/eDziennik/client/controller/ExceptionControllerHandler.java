package pl.edziennik.eDziennik.client.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.edziennik.eDziennik.exceptions.BusinessException;
import pl.edziennik.eDziennik.exceptions.EntityNotFoundException;
import pl.edziennik.eDziennik.server.basics.ApiErrorsDto;
import pl.edziennik.eDziennik.server.basics.ApiResponse;
import pl.edziennik.eDziennik.server.basics.BaseDao;

import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
@RestController
@Slf4j
public class ExceptionControllerHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {EntityNotFoundException.class, NoResultException.class})
    protected ResponseEntity<ApiResponse> handleException(RuntimeException exception, WebRequest request) throws URISyntaxException {
        HttpServletRequest httpRequest = ((ServletWebRequest) request).getRequest();
        URI uri = new URI(httpRequest.getRequestURL().toString());
        List<ApiErrorsDto> errors = List.of(new ApiErrorsDto(null, exception.getMessage(), false, BaseDao.class.getName()));
        log.error("ERROR ON PATH " + request.getDescription(false) + " ERROR MESSAGE: " + exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.buildApiResponse(HttpMethod.valueOf(httpRequest.getMethod()), HttpStatus.NOT_FOUND,uri, errors));
    }

    @ExceptionHandler(value = BusinessException.class)
    protected ResponseEntity<ApiResponse> handleBusinessException(BusinessException exception, WebRequest request) throws URISyntaxException {
        HttpServletRequest httpRequest = ((ServletWebRequest) request).getRequest();
        URI uri = new URI(httpRequest.getRequestURL().toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.buildApiResponse(HttpMethod.valueOf(httpRequest.getMethod()), HttpStatus.BAD_REQUEST,uri, exception.getErrors()));
    }

    @InitBinder
    protected void loggerWithParam(WebDataBinder binder, WebRequest webRequest){
        HttpServletRequest request = ((ServletWebRequest) webRequest).getRequest();
        String method = request.getMethod().toLowerCase();
        String URI = request.getRequestURI();
        log.info("CALLED | " + URI + " | METHOD USED: | " + method + " | WITH OBJECT | " + binder.getObjectName() + " |");
    }
}
