package pl.edziennik.eDziennik.server.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Locale;

@Component
public class ResourceCreator {


    @Autowired
    private MessageSource messageSource;

    public String of(String errorCode, Object... objects){
        return messageSource.getMessage(errorCode, objects, LocaleContextHolder.getLocale());
    }

    public String of(String errorCode){
        return messageSource.getMessage(errorCode,null, LocaleContextHolder.getLocale());
    }

}
