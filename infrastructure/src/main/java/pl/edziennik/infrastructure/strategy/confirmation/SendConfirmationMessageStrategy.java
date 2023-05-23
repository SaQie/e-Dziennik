package pl.edziennik.infrastructure.strategy.confirmation;

/**
 * Sending confirmation message strategy
 */
public interface SendConfirmationMessageStrategy {

    void sendMessage(SendConfirmationMessageStrategyData data);

    boolean isEnabled();
}
