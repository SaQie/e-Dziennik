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
public class SchoolClassId implements Serializable, Identifier {

    @JsonValue
    private final UUID id;

    public static SchoolClassId create() {
        return new SchoolClassId(UUID.randomUUID());
    }

    @JsonCreator
    protected SchoolClassId(String value) {
        try {
            this.id = UUID.fromString(value);
        } catch (IllegalArgumentException e) {
            throw new InvalidIdentifierException("Invalid identifier");
        }
    }

    protected SchoolClassId(UUID uuid) {
        this.id = uuid;
    }

    public static SchoolClassId of(UUID uuid) {
        return new SchoolClassId(uuid);
    }

    public static SchoolClassId of(String value) {
        return new SchoolClassId(value);
    }


}