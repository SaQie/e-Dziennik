package pl.edziennik.infrastructure.validator;

import com.fasterxml.jackson.annotation.JsonInclude;
import pl.edziennik.infrastructure.validator.errorcode.ErrorCode;

/**
 * Object that represent specific error
 *
 * @param field
 * @param message
 * @param errorCode
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ValidationError(
        String field,
        Integer errorCode,
        String message,
        String detail
) {

    @Override
    public String toString() {
        return "[Field: '" + field + "' ]-[ErrorMessage: '" + message + "' ]-[ErrorCode: '" + errorCode + "' ]";
    }

    public ValidationError(String field, String message, Integer errorCode) {
        this(field, errorCode, message, null);
    }

    public ValidationError(String field, String message, ErrorCode errorCode) {
        this(field, errorCode.errorCode(), message, null);
    }
}
