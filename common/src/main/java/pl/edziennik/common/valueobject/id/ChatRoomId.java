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
public class ChatRoomId implements Serializable, Identifier {


    @JsonValue
    private final UUID id;

    public static ChatRoomId create() {
        return new ChatRoomId(UUID.randomUUID());
    }

    @JsonCreator
    protected ChatRoomId(String value) {
        try {
            this.id = UUID.fromString(value);
        } catch (IllegalArgumentException e) {
            throw new InvalidIdentifierException("Invalid identifier");
        }
    }

    protected ChatRoomId(UUID uuid) {
        this.id = uuid;
    }


    public static ChatRoomId of(UUID uuid) {
        return new ChatRoomId(uuid);
    }

    public static ChatRoomId of(String value) {
        return new ChatRoomId(value);
    }

}