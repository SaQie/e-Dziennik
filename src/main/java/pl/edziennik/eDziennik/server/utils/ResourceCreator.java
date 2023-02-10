package pl.edziennik.eDziennik.server.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

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
    public String of(String errorCode, Object... objects){
        return messageSource.getMessage(errorCode, objects, LocaleContextHolder.getLocale());
    }

    /**
     * Return translated message
     */
    public String of(String errorCode){
        return messageSource.getMessage(errorCode,null, LocaleContextHolder.getLocale());
    }
}
