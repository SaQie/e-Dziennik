package pl.edziennik.application.events.event;

import pl.edziennik.common.valueobject.id.UserId;

public record UserAccountCreatedEvent(

        UserId userId

) {
}
