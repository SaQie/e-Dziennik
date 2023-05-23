package pl.edziennik.application.events.listener;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import pl.edziennik.application.events.event.UserAccountCreatedEvent;
import pl.edziennik.infrastructure.strategy.confirmation.SendConfirmationMessageStrategy;
import pl.edziennik.infrastructure.strategy.confirmation.SendConfirmationMessageStrategyData;

import java.util.List;

@Component
@AllArgsConstructor
public class UserEventListener {

    private final List<SendConfirmationMessageStrategy> confirmationMessageStrategies;

    @Async
    @TransactionalEventListener(classes = UserAccountCreatedEvent.class,
            phase = TransactionPhase.AFTER_COMMIT)
    public void onUserAccountCreatedEvent(UserAccountCreatedEvent event){
        confirmationMessageStrategies.stream()
                .filter(SendConfirmationMessageStrategy::isEnabled)
                .forEach(strategy ->
                        strategy.sendMessage(new SendConfirmationMessageStrategyData(event.userId())));

    }

}
