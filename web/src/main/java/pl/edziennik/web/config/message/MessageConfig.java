package pl.edziennik.web.config.message;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Locale;

/**
 * Configuration for internationalization
 */
@Configuration
public class MessageConfig {

    @Bean
    public MessageSource messageSource(){
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("classpath:/messages/error_messages");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setDefaultLocale(new Locale("en", "EN"));
        return messageSource;
    }

    @Bean
    public AcceptHeaderLocaleResolver localeResolver(){
        final AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
        localeResolver.setDefaultLocale(new Locale("en", "EN"));
        return localeResolver;
    }


}
