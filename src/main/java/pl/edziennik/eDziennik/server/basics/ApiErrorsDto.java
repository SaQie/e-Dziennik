package pl.edziennik.eDziennik.server.basics;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

@Getter
public class ApiErrorsDto {

    private final String field;
    private final String cause;

    @JsonIgnore
    private final boolean thrownImmediately;

    public ApiErrorsDto(String field, String cause, boolean thrownImmediately) {
        this.field = field;
        this.cause = cause;
        this.thrownImmediately = thrownImmediately;
    }
}
