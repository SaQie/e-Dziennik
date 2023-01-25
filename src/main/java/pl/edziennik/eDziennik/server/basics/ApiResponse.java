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



}
