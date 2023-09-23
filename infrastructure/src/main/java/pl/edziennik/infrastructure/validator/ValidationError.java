package pl.edziennik.infrastructure.validator;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
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
        @Schema(example = "teacherId")
        String field,

        @Schema(example = "1000")
        Integer errorCode,

        @Schema(example = "Object with given id not exist")
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
