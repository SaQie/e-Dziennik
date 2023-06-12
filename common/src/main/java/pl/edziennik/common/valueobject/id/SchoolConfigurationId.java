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
public class SchoolConfigurationId implements Identifier, Serializable {

    @JsonValue
    private final UUID id;

    public static SchoolConfigurationId create() {
        return new SchoolConfigurationId(UUID.randomUUID());
    }

    @JsonCreator
    protected SchoolConfigurationId(String value) {
        try {
            this.id = UUID.fromString(value);
        } catch (IllegalArgumentException e) {
            throw new InvalidIdentifierException("Invalid identifier");
        }
    }

    protected SchoolConfigurationId(UUID uuid) {
        this.id = uuid;
    }

    public static SchoolConfigurationId of(UUID uuid) {
        return new SchoolConfigurationId(uuid);
    }

    public static SchoolConfigurationId of(String value) {
        return new SchoolConfigurationId(value);
    }

}
