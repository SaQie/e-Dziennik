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
public class UserId implements Serializable, Identifier {

    @JsonValue
    private final UUID id;

    public static UserId create() {
        return new UserId(UUID.randomUUID());
    }

    @JsonCreator
    protected UserId(String value) {
        try {
            this.id = UUID.fromString(value);
        } catch (IllegalArgumentException e) {
            throw new InvalidIdentifierException("Invalid identifier");
        }
    }

    protected UserId(UUID uuid) {
        this.id = uuid;
    }


    public static UserId of(UUID uuid) {
        return new UserId(uuid);
    }

    public static UserId of(String value) {
        return new UserId(value);
    }

}