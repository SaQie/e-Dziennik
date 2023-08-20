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
public class SchoolClassConfigurationId implements Identifier, Serializable {

    @JsonValue
    private final UUID id;

    public static SchoolClassConfigurationId create() {
        return new SchoolClassConfigurationId(UUID.randomUUID());
    }

    @JsonCreator
    protected SchoolClassConfigurationId(String value) {
        try {
            this.id = UUID.fromString(value);
        } catch (IllegalArgumentException e) {
            throw new InvalidIdentifierException("Invalid identifier");
        }
    }

    protected SchoolClassConfigurationId(UUID uuid) {
        this.id = uuid;
    }

    public static SchoolClassConfigurationId of(UUID uuid) {
        return new SchoolClassConfigurationId(uuid);
    }

    public static SchoolClassConfigurationId of(String value) {
        return new SchoolClassConfigurationId(value);
    }


}
