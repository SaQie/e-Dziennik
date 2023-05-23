package pl.edziennik.infrastructure.strategy.confirmation.email;

import pl.edziennik.common.valueobject.Token;
import pl.edziennik.common.valueobject.id.UserId;

/**
 * Email confirmation message view strategy
 */
public interface EmailConfirmationMessageViewStrategy {

    String getHTMLMessage(Token token, UserId userId);

    boolean isEnabled();

}
