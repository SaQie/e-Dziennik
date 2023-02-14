package pl.edziennik.eDziennik;

import org.springframework.context.annotation.Import;
import org.springframework.context.support.ResourceBundleMessageSource;
import pl.edziennik.eDziennik.server.config.messages.MessageConfig;

import java.util.Locale;

@Import(MessageConfig.class)
public class BaseUnitTest {

    protected String getErrorMessage(String messageKey) {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setUseCodeAsDefaultMessage(true);
        return messageSource.getMessage(messageKey, null, Locale.ROOT);
    }

}
