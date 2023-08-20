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
public class TeacherId implements Serializable, Identifier {

    @JsonValue
    private final UUID id;

    public static TeacherId create() {
        return new TeacherId(UUID.randomUUID());
    }


    @JsonCreator
    protected TeacherId(String value) {
        try {
            this.id = UUID.fromString(value);
        } catch (IllegalArgumentException e) {
            throw new InvalidIdentifierException("Invalid identifier");
        }
    }

    protected TeacherId(UUID uuid) {
        this.id = uuid;
    }


    public static TeacherId of(UUID uuid) {
        return new TeacherId(uuid);
    }

    public static TeacherId of(String value) {
        return new TeacherId(value);
    }

}