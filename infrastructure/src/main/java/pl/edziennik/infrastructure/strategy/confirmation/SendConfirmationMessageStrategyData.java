package pl.edziennik.infrastructure.strategy.confirmation;

import pl.edziennik.common.valueobject.id.UserId;

public record SendConfirmationMessageStrategyData(
        UserId userId
) {
}
