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
import pl.edziennik.common.valueobject.base.Identifier;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Getter
@Accessors(fluent = true)
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class ClassRoomId implements Serializable, Identifier {

    @JsonValue
    private final UUID id;

    public static ClassRoomId create() {
        return new ClassRoomId(UUID.randomUUID());
    }

    @JsonCreator
    protected ClassRoomId(String value) {
        try {
            this.id = UUID.fromString(value);
        } catch (IllegalArgumentException e) {
            throw new InvalidIdentifierException("Invalid identifier");
        }
    }

    protected ClassRoomId(UUID uuid) {
        this.id = uuid;
    }


    public static ClassRoomId of(UUID uuid) {
        return new ClassRoomId(uuid);
    }

    public static ClassRoomId of(String value) {
        return new ClassRoomId(value);
    }

}
