package pl.edziennik.eDziennik.client.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import pl.edziennik.eDziennik.exceptions.EntityNotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@ControllerAdvice
@RestController
@Slf4j
public class ExceptionControllerHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {EntityNotFoundException.class, NoResultException.class})
    protected ResponseEntity<?> handleException(RuntimeException exception, WebRequest request){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", exception.getMessage());
        body.put("code", HttpStatus.NOT_FOUND.value());
        body.put("path", request.getDescription(false).substring(4));
        log.error("ERROR ON PATH " + request.getDescription(false) + " ERROR MESSAGE: " + exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(value = EntityExistsException.class)
    protected ResponseEntity<?> handleExistException(PersistenceException e, WebRequest request){
        Map<String,Object> body = new LinkedHashMap<>();
        body.put("timestamp",LocalDateTime.now());
        body.put("message", e.getMessage());
        body.put("code", HttpStatus.CONFLICT.value());
        body.put("patch", request.getDescription(false).substring(4));
        log.error("ERROR ON PATH " + request.getDescription(false) + " ERROR MESSAGE: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    @InitBinder
    protected void loggerWithParam(WebDataBinder binder, WebRequest webRequest){
        HttpServletRequest request = ((ServletWebRequest) webRequest).getRequest();
        String method = request.getMethod().toLowerCase();
        String URI = request.getRequestURI();
        log.info("CALLED | " + URI + " | METHOD USED: | " + method + " | WITH OBJECT | " + binder.getObjectName() + " |");
    }
}
