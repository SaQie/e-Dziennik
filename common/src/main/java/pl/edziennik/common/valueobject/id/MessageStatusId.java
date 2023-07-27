package pl.edziennik.common.valueobject.id;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import pl.edziennik.common.exception.InvalidIdentifierException;
import pl.edziennik.common.valueobject.Identifier;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Getter
@Accessors(fluent = true)
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class MessageStatusId implements Serializable, Identifier {

    @JsonValue
    private final UUID id;

    public static MessageStatusId create() {
        return new MessageStatusId(UUID.randomUUID());
    }

    @JsonCreator
    protected MessageStatusId(String value) {
        try {
            this.id = UUID.fromString(value);
        } catch (IllegalArgumentException e) {
            throw new InvalidIdentifierException("Invalid identifier");
        }
    }

    protected MessageStatusId(UUID uuid) {
        this.id = uuid;
    }


    public static MessageStatusId of(UUID uuid) {
        return new MessageStatusId(uuid);
    }

    public static MessageStatusId of(String value) {
        return new MessageStatusId(value);
    }

    public static class PredefinedRow {

        public static final MessageStatusId DELIVERED = MessageStatusId.of("36572198-2cab-11ee-be56-0242ac120002");
        public static final MessageStatusId RECEIVED = MessageStatusId.of("36571ed2-2cab-11ee-be56-0242ac120002");

    }

}
