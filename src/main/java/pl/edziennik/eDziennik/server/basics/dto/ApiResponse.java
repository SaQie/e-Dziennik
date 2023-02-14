package pl.edziennik.eDziennik.server.basics.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.net.URI;
import java.util.List;

/**
 * Basic class for rest api response
 * @param <OUTPUT>
 */
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ApiResponse<OUTPUT> {

    private String method;
    private int code;
    private String status;
    private URI url;
    private String executionTime;
    private String operationDescription;
    private List<ApiErrorDto> errors;
    private OUTPUT result;



}
