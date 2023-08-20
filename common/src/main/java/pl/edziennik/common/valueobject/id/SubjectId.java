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
public class SubjectId implements Serializable, Identifier {

    @JsonValue
    private final UUID id;

    public static SubjectId create() {
        return new SubjectId(UUID.randomUUID());
    }

    @JsonCreator
    protected SubjectId(String value) {
        try {
            this.id = UUID.fromString(value);
        } catch (IllegalArgumentException e) {
            throw new InvalidIdentifierException("Invalid identifier");
        }
    }

    protected SubjectId(UUID uuid) {
        this.id = uuid;
    }


    public static SubjectId of(UUID uuid) {
        return new SubjectId(uuid);
    }

    public static SubjectId of(String value) {
        return new SubjectId(value);
    }

}