package pl.edziennik.eDziennik.server.basics;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@AllArgsConstructor
public class ApiErrorsDto {

    private final List<String> fields;
    private final String cause;
    private final ExceptionType exceptionType;
    @JsonIgnore
    private final String errorThrownedBy;

    @JsonIgnore
    private final boolean thrownImmediately;

    public ApiErrorsDto(List<String> fields, String cause, boolean thrownImmediately, String errorThrownedBy, ExceptionType type) {
        this.fields = fields;
        this.cause = cause;
        this.thrownImmediately = thrownImmediately;
        this.errorThrownedBy = errorThrownedBy;
        this.exceptionType = type;
    }
}
