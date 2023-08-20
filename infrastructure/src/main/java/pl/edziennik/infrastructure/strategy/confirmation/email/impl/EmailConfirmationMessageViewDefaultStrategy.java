package pl.edziennik.infrastructure.strategy.confirmation.email.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.edziennik.common.valueobject.vo.Token;
import pl.edziennik.common.valueobject.id.UserId;
import pl.edziennik.infrastructure.strategy.confirmation.email.EmailConfirmationMessageViewStrategy;

@Component
public class EmailConfirmationMessageViewDefaultStrategy implements EmailConfirmationMessageViewStrategy {

    @Value("${application.address}")
    private String applicationAddress;

    @Override
    public String getHTMLMessage(Token token, UserId userId) {
        return applicationAddress + "api/v1/users/activate?token=" + token.value().toString();
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
