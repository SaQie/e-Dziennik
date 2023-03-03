package pl.edziennik.eDziennik.server.basics.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import pl.edziennik.eDziennik.server.exceptions.ExceptionType;

/**
 * Dto class for errors
 */
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@AllArgsConstructor
@EqualsAndHashCode
public class ApiErrorDto {

    private final String field;
    private final String cause;
    private final ExceptionType exceptionType;

    @JsonIgnore
    private final String errorThrownedBy;

    @JsonIgnore
    private final boolean thrownImmediately;

    public ApiErrorDto(String field, String cause, boolean thrownImmediately, String errorThrownedBy, ExceptionType type) {
        this.field = field;
        this.cause = cause;
        this.thrownImmediately = thrownImmediately;
        this.errorThrownedBy = errorThrownedBy;
        this.exceptionType = type;
    }


}