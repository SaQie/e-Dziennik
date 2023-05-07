package pl.edziennik.infrastructure.validator;

/**
 * Object that represent specific error
 *
 * @param field
 * @param errorMessage
 * @param errorCode
 */
public record ValidationError(
        String field,
        String errorMessage,
        Integer errorCode
) {

    @Override
    public String toString() {
        return "[Field: '" + field + "' ]-[ErrorMessage: '" + errorMessage + "' ]-[ErrorCode: '" + errorCode + "' ]";
    }
}
