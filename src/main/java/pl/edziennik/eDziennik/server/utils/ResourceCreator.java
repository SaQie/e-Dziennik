package pl.edziennik.eDziennik.server.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import pl.edziennik.eDziennik.infrastructure.validator.ValidationError;
import pl.edziennik.eDziennik.infrastructure.validator.errorcode.ErrorCode;
import pl.edziennik.eDziennik.server.basic.vo.Identifier;

/**
 * Utility class for getting translated messages
 */
@Component
public class ResourceCreator {

    @Autowired
    private MessageSource messageSource;

    /**
     * Return translated message with params
     */
    public String of(String errorCode, Object... objects) {
        return messageSource.getMessage(errorCode, objects, LocaleContextHolder.getLocale());
    }

    /**
     * Return translated message
     */
    public String of(String errorMessageKey) {
        return messageSource.getMessage(errorMessageKey, null, LocaleContextHolder.getLocale());
    }

    public ValidationError notFoundError(String field, Identifier id) {
        return new ValidationError(field, of("not.found.message", id.id(), id.getClass().getSimpleName()), ErrorCode.OBJECT_NOT_EXISTS.errorCode());
    }
}
