package pl.edziennik.eDziennik.server.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class ResourceCreator {

    private Locale locale = Locale.ROOT;

    @Autowired
    private MessageSource messageSource;

    public String of(String errorCode, Object... objects){
        return messageSource.getMessage(errorCode, objects, locale);
    }

    public String of(String errorCode){
        return messageSource.getMessage(errorCode,null,locale);
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
