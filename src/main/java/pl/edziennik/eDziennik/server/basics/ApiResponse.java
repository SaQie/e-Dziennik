package pl.edziennik.eDziennik.server.basics;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.SneakyThrows;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ApiResponse<T> {

    private String method;
    private int code;
    private String status;
    private URI url;
    private String executionTime;
    private String operationDescription;
    private List<ApiErrorsDto> errors;
    private String stackTrace;
    private T result;

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

    public static<T> ApiResponse<T> buildApiResponse(HttpMethod method, HttpStatus status, URI url, List<ApiErrorsDto> errors, String stackTrace){
        return ApiResponse.
                <T>builder()
                .method(method.name())
                .status(status.name())
                .code(status.value())
                .url(url)
                .errors(errors)
                .stackTrace(stackTrace)
                .executionTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
    }

    @SneakyThrows
    public static<T> ApiResponse<T> buildApiResponse(HttpMethod method, HttpStatus status, String url, List<ApiErrorsDto> errors, String stackTrace){
        return ApiResponse.
                <T>builder()
                .method(method.name())
                .status(status.name())
                .code(status.value())
                .url(new URI(url))
                .errors(errors)
                .stackTrace(stackTrace)
                .executionTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
    }

}
