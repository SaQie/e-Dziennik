package pl.edziennik.eDziennik.server.basics;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
public class ApiErrorsDto {

    private String field;
    private String cause;

}
