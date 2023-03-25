package pl.edziennik.eDziennik.server.basics.dto;

import lombok.SneakyThrows;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import pl.edziennik.eDziennik.server.exceptions.BusinessException;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *  Builder class for rest api response
 */
public class ApiResponseCreator {

    public static<T> ApiResponse<T> buildApiResponse(HttpMethod method, HttpStatus status, T result, URI url){
        return ApiResponse.
                <T>builder()
                .method(method.name())
                .status(status.name())
                .code(status.value())
                .url(url)
                .executionTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .result(result)
                .build();
    }

    public static<T> ApiResponse<T> buildApiResponse(HttpMethod method,HttpStatus status, String description, URI url){
        return ApiResponse.
                <T>builder()
                .method(method.name())
                .status(status.name())
                .code(status.value())
                .url(url)
                .operationDescription(description)
                .executionTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
    }

    public static<T> ApiResponse<T> buildApiResponse(HttpMethod method, HttpStatus status, URI url, BusinessException errors){
        return ApiResponse.
                <T>builder()
                .method(method.name())
                .status(status.name())
                .code(status.value())
                .url(url)
                .errors(errors.getErrors())
                .executionTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
    }

    public static<T> ApiResponse<T> buildApiResponse(HttpMethod method, HttpStatus status, URI url, List<ApiValidationResult> errors){
        return ApiResponse.
                <T>builder()
                .method(method.name())
                .status(status.name())
                .code(status.value())
                .url(url)
                .errors(errors)
                .executionTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
    }

    @SneakyThrows
    public static<T> ApiResponse<T> buildApiResponse(HttpMethod method, HttpStatus status, String url, List<ApiValidationResult> errors){
        return ApiResponse.
                <T>builder()
                .method(method.name())
                .status(status.name())
                .code(status.value())
                .url(new URI(url))
                .errors(errors)
                .executionTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
    }

}
